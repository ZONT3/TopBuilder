package ru.zont.topbuilder.core;

import java.util.HashMap;

public class DecisionEntry<T> {
    private final T lhs;
    private final T rhs;
    private final int decision;
    protected final HashMap<String, Object> data;

    public DecisionEntry(T lhs, T rhs, int decision) {
        this(lhs, rhs, decision, new HashMap<>());
    }

    private DecisionEntry(T lhs, T rhs, int decision, HashMap<String, Object> data) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.decision = decision;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public <K> K get(String key) {
        try {
            return (K) data.get(key);
        } catch (ClassCastException e) {
            new RuntimeException("Type mismatch", e).printStackTrace();
            return null;
        }
    }

    public T getLhs() {
        return lhs;
    }

    public T getRhs() {
        return rhs;
    }

    public int getDecision() {
        return decision;
    }

    static Builder builder() { return new Builder(); }

    static class Builder {
        private final HashMap<String, Object> map;

        private Builder() {
            map = new HashMap<>();
        }

        Builder put(String key, Object val) {
            map.put(key, val);
            return this;
        }

        <T> DecisionEntry<T> build(T lhs, T rhs, int decision) {
            return new DecisionEntry<T>(lhs, rhs, decision, map);
        }
    }
}
