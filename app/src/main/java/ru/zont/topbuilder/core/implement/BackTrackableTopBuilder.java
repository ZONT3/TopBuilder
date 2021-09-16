package ru.zont.topbuilder.core.implement;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import ru.zont.topbuilder.core.DecisionEntry;
import ru.zont.topbuilder.core.Tools;

public abstract class BackTrackableTopBuilder<T> extends BasicTopBuilder<T> {
    private int states = 0;
    private File directory;

    public void setStorage(File directory) throws IOException {
        if (!directory.isDirectory()) throw new IllegalArgumentException("Not a dir");

        final File test = new File(directory, "info");
        if (test.exists() && !test.delete()) throw new IOException("Cannot access directory");
        if (!test.createNewFile()) throw new IOException("Cannot create file in directory");
        if (!test.delete()) throw new IOException("Cannot delete file in directory");

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test))) {
            out.writeObject(Calendar.getInstance());
        }

        final Random r = new Random(System.currentTimeMillis());
        File f;
        do { f = new File(directory, Tools.genNewStr(r)); }
        while (f.exists());
        this.directory = directory;
    }

    public void changeDecision(int i, int decision) {
        chk();
        if (states <= i) throw new IllegalStateException("Invalid index");

        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(getStateFile(i)))) {
            restoreState((Serializable[]) out.readObject());
            provideDecision(decision);

            final int count = states - i;
            final List<DecisionEntry<T>> future = cutHistory(count);
            for (int j = 1; j < future.size(); j++) {
                final DecisionEntry<T> currDecision = future.get(j);
                next((lhs, rhs) -> {
                    if (currDecision.getLhs() != lhs || currDecision.getRhs() != rhs)
                        throw new IllegalStateException("Desync with history");
                });
                provideDecision(decision);
            }
            trimHistory(count);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builder must be waiting for decision after this operation
     * @param objects objects, saved earlier by {@link BackTrackableTopBuilder#storeState(Serializable...)}
     */
    protected abstract void restoreState(Serializable[] objects);

    protected void storeState(Serializable... objects) {
        chk();
        final File file = getStateFile(states);
        if (file.exists() && !file.delete()) throw new RuntimeException("File exists and cannot be deleted");

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(objects);
            out.flush();
            states++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void chk() {
        if (directory == null || !directory.isDirectory())
            throw new IllegalStateException("Cache directory not initialized");
    }

    @NonNull
    private File getStateFile(int i) {
        return new File(directory, "state_" + i);
    }
}
