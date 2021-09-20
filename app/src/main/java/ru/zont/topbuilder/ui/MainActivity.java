package ru.zont.topbuilder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Constructor;

import ru.zont.topbuilder.core.TopBuilder;
import ru.zont.topbuilder.databinding.ActivityMainBinding;
import ru.zont.topbuilder.ui.data.TopBuilderInfo;
import ru.zont.topbuilder.ui.data.TopItem;
import ru.zont.topbuilder.ui.data.TopList;
import ru.zont.topbuilder.ui.data.info.EloTopBuilderInfo;
import ru.zont.topbuilder.ui.data.info.KothTopBuilderInfo;
import ru.zont.topbuilder.ui.data.info.WeightTopBuilderInfo;

public class MainActivity extends AppCompatActivity {

    private TopList providedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        providedList = tempList3();

        binding.btW.setOnClickListener(v -> onClickButton(new WeightTopBuilderInfo()));
        binding.btE.setOnClickListener(v -> onClickButton(new EloTopBuilderInfo()));
        binding.btKoth.setOnClickListener(v -> onClickButton(new KothTopBuilderInfo()));
    }

    private <T extends TopBuilder<? extends TopItem>> void onClickButton(TopBuilderInfo<T> info) {
        final Intent intent = new Intent(MainActivity.this, ActionActivity.class);
        intent.putExtra("z.top.list", (Parcelable) providedList);
        intent.putExtra("z.top.class", info.getClass());
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    static TopBuilderInfo<? extends TopBuilder<? extends TopItem>> getTopBuilderInfo(Intent intent) {
        TopBuilderInfo<? extends TopBuilder<? extends TopItem>> info = null;
        try {
            Class<? extends TopBuilderInfo<? extends TopBuilder<? extends TopItem>>> klass
                    = (Class<? extends TopBuilderInfo<? extends TopBuilder<? extends TopItem>>>)
                    intent.getSerializableExtra("z.top.class");
            Constructor<? extends TopBuilderInfo<? extends TopBuilder<? extends TopItem>>> constructor
                    = klass.getConstructor();

            info = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    private TopList tempList3() {
        return new TopList() {{
            add(new TopItem("M870", "https://iopwiki.com/images/3/3d/M870_S.png"));
            add(new TopItem("El Fail", "https://iopwiki.com/images/thumb/b/be/Fail_S.png/128px-Fail_S.png"));
            add(new TopItem("GSh-18", "https://iopwiki.com/images/thumb/5/5d/GSh-18_S.png/128px-GSh-18_S.png"));
            add(new TopItem("AN-94", "https://iopwiki.com/images/thumb/3/3b/AN-94_S.png/128px-AN-94_S.png"));
            add(new TopItem("RO635", "https://iopwiki.com/images/thumb/5/5a/RO635_S.png/128px-RO635_S.png"));
        }};
    }

    private TopList tempList2() {
        return new TopList() {{
            add(new TopItem("Mavor", "https://static.wikia.nocookie.net/supcom/images/a/a5/UEB2401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316195204"));
            add(new TopItem("Novax", "https://static.wikia.nocookie.net/supcom/images/b/b6/XEB2402_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080602103428"));
            add(new TopItem("Atlantis", "https://static.wikia.nocookie.net/supcom/images/8/8f/UES0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316203059"));
            add(new TopItem("Fatboy", "https://static.wikia.nocookie.net/supcom/images/e/e5/UEL0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20090302225042"));
            add(new TopItem("Paragon", "https://static.wikia.nocookie.net/supcom/images/c/c9/XAB1401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080602101715"));
            add(new TopItem("Salvation", "https://static.wikia.nocookie.net/supcom/images/1/19/XAB2307_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080602103539"));
            add(new TopItem("Galactic Colossus", "https://static.wikia.nocookie.net/supcom/images/9/93/UAL0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316192045"));
            add(new TopItem("Tempest", "https://static.wikia.nocookie.net/supcom/images/4/4e/UAS0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080505023613"));
            add(new TopItem("CZAR", "https://static.wikia.nocookie.net/supcom/images/4/47/UAA0310_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316030643"));
            add(new TopItem("Monkeylord", "https://static.wikia.nocookie.net/supcom/images/6/6d/URL0402_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316211951"));
            add(new TopItem("Megalith", "https://static.wikia.nocookie.net/supcom/images/3/36/XRL0403_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080602111012"));
            add(new TopItem("Soul Ripper", "https://static.wikia.nocookie.net/supcom/images/0/00/URA0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316203745"));
            add(new TopItem("Schathis", "https://static.wikia.nocookie.net/supcom/images/3/3e/URL0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20070316211920"));
            add(new TopItem("Ythotha", "https://static.wikia.nocookie.net/supcom/images/5/5c/XSL0401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080515015541"));
            add(new TopItem("Yolona Oss", "https://static.wikia.nocookie.net/supcom/images/a/a4/XSB2401_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080515015242"));
            add(new TopItem("Ahwassa", "https://static.wikia.nocookie.net/supcom/images/9/99/XSA0402_build_btn.png/revision/latest/scale-to-width-down/64?cb=20080515015025"));
        }};
    }

    private TopList tempList() {
        return new TopList() {{
//            add(new TopItem("SAA", "https://iopwiki.com/images/thumb/a/a3/Colt_Revolver_S.png/128px-Colt_Revolver_S.png"));
            add(new TopItem("M1911", "https://iopwiki.com/images/thumb/b/b7/M1911_S.png/128px-M1911_S.png"));
//            add(new TopItem("M9", "https://iopwiki.com/images/thumb/0/0a/M9_S.png/128px-M9_S.png"));
            add(new TopItem("Python", "https://iopwiki.com/images/thumb/9/98/Python_S.png/128px-Python_S.png"));
//            add(new TopItem("M1895", "https://iopwiki.com/images/thumb/1/1d/Nagant_Revolver_S.png/128px-Nagant_Revolver_S.png"));
//            add(new TopItem("Tokarev", "https://iopwiki.com/images/thumb/7/7f/Tokarev_S.png/128px-Tokarev_S.png"));
//            add(new TopItem("Stechkin", "https://iopwiki.com/images/thumb/f/f4/Stechkin_S.png/128px-Stechkin_S.png"));
//            add(new TopItem("Makarov", "https://iopwiki.com/images/thumb/1/10/Makarov_S.png/128px-Makarov_S.png"));
//            add(new TopItem("P38", "https://iopwiki.com/images/thumb/d/db/P38_S.png/128px-P38_S.png"));
//            add(new TopItem("PPK", "https://iopwiki.com/images/thumb/0/0e/PPK_S.png/128px-PPK_S.png"));
//            add(new TopItem("P08", "https://iopwiki.com/images/thumb/a/a2/P08_S.png/128px-P08_S.png"));
//            add(new TopItem("C96", "https://iopwiki.com/images/thumb/4/4b/C96_S.png/128px-C96_S.png"));
//            add(new TopItem("Type 92", "https://iopwiki.com/images/thumb/0/0f/Type_92_S.png/128px-Type_92_S.png"));
//            add(new TopItem("Astra", "https://iopwiki.com/images/thumb/c/c6/Astra_Revolver_S.png/128px-Astra_Revolver_S.png"));
//            add(new TopItem("G17", "https://iopwiki.com/images/thumb/2/2c/Glock_17_S.png/128px-Glock_17_S.png"));
//            add(new TopItem("Thompson", "https://iopwiki.com/images/thumb/5/5d/Thompson_S.png/128px-Thompson_S.png"));
//            add(new TopItem("M3", "https://iopwiki.com/images/thumb/5/5f/M3_S.png/128px-M3_S.png"));
//            add(new TopItem("Ingram", "https://iopwiki.com/images/thumb/7/7f/MAC-10_S.png/128px-MAC-10_S.png"));
            add(new TopItem("FMG-9", "https://iopwiki.com/images/thumb/d/d8/FMG-9_S.png/128px-FMG-9_S.png"));
            add(new TopItem("Vector", "https://iopwiki.com/images/thumb/6/69/Vector_S.png/128px-Vector_S.png"));
//            add(new TopItem("PPSh-41", "https://iopwiki.com/images/thumb/3/31/PPSh-41_S.png/128px-PPSh-41_S.png"));
//            add(new TopItem("PPS-43", "https://iopwiki.com/images/thumb/f/fb/PPS-43_S.png/128px-PPS-43_S.png"));
//            add(new TopItem("PP-90", "https://iopwiki.com/images/thumb/1/1d/PP-90_S.png/128px-PP-90_S.png"));
//            add(new TopItem("PP-2000", "https://iopwiki.com/images/thumb/3/38/PP-2000_S.png/128px-PP-2000_S.png"));
//            add(new TopItem("MP40", "https://iopwiki.com/images/thumb/6/69/MP40_S.png/128px-MP40_S.png"));
//            add(new TopItem("MP5", "https://iopwiki.com/images/thumb/e/e5/MP5_S.png/128px-MP5_S.png"));
//            add(new TopItem("Skorpion", "https://iopwiki.com/images/thumb/e/e7/Skorpion_S.png/128px-Skorpion_S.png"));
//            add(new TopItem("MP7", "https://iopwiki.com/images/thumb/4/47/MP7_S.png/128px-MP7_S.png"));
            add(new TopItem("STEN MkII", "https://iopwiki.com/images/thumb/2/21/Sten_MkII_S.png/128px-Sten_MkII_S.png"));
//            add(new TopItem("M38", "https://iopwiki.com/images/thumb/5/50/Beretta_Model_38_S.png/128px-Beretta_Model_38_S.png"));
            add(new TopItem("Micro Uzi", "https://iopwiki.com/images/thumb/7/71/Micro_Uzi_S.png/128px-Micro_Uzi_S.png"));
//            add(new TopItem("m45", "https://iopwiki.com/images/thumb/8/8a/m45_S.png/128px-m45_S.png"));
            add(new TopItem("M1 Garand", "https://iopwiki.com/images/thumb/1/1d/M1_Garand_S.png/128px-M1_Garand_S.png"));
//            add(new TopItem("M1A1", "https://iopwiki.com/images/thumb/9/9a/M1A1_S.png/128px-M1A1_S.png"));
            add(new TopItem("Springfield", "https://iopwiki.com/images/thumb/3/3d/Springfield_S.png/128px-Springfield_S.png"));
            add(new TopItem("M14", "https://iopwiki.com/images/thumb/7/76/M14_S.png/128px-M14_S.png"));
//            add(new TopItem("M21", "https://iopwiki.com/images/thumb/d/da/M21_S.png/128px-M21_S.png"));
            add(new TopItem("Mosin-Nagant", "https://iopwiki.com/images/thumb/9/97/Mosin-Nagant_S.png/128px-Mosin-Nagant_S.png"));
//            add(new TopItem("SVT-38", "https://iopwiki.com/images/thumb/2/26/SVT-38_S.png/128px-SVT-38_S.png"));
//            add(new TopItem("SKS", "https://iopwiki.com/images/thumb/2/22/Simonov_S.png/128px-Simonov_S.png"));
//            add(new TopItem("PTRD", "https://iopwiki.com/images/thumb/e/ed/PTRD_S.png/128px-PTRD_S.png"));
//            add(new TopItem("SVD", "https://iopwiki.com/images/thumb/3/3e/SVD_S.png/128px-SVD_S.png"));
            add(new TopItem("SV-98", "https://iopwiki.com/images/thumb/8/80/SV-98_S.png/128px-SV-98_S.png"));
//            add(new TopItem("Kar98k", "https://iopwiki.com/images/thumb/4/4d/Kar98k_S.png/128px-Kar98k_S.png"));
//            add(new TopItem("G43", "https://iopwiki.com/images/thumb/b/b3/G43_S.png/128px-G43_S.png"));
            add(new TopItem("WA2000", "https://iopwiki.com/images/thumb/5/54/WA2000_S.png/128px-WA2000_S.png"));
//            add(new TopItem("Lee-Enfield", "https://iopwiki.com/images/thumb/c/cb/Lee-Enfield_S.png/128px-Lee-Enfield_S.png"));
//            add(new TopItem("FN49", "https://iopwiki.com/images/thumb/a/ac/FN-49_S.png/128px-FN-49_S.png"));
//            add(new TopItem("VM59", "https://iopwiki.com/images/thumb/b/b3/BM59_S.png/128px-BM59_S.png"));
            add(new TopItem("NTW-20", "https://iopwiki.com/images/thumb/a/a0/NTW-20_S.png/128px-NTW-20_S.png"));
            add(new TopItem("M16A1", "https://iopwiki.com/images/thumb/7/7e/M16A1_S.png/128px-M16A1_S.png"));
            add(new TopItem("M4A1", "https://iopwiki.com/images/thumb/c/cb/M4A1_S.png/128px-M4A1_S.png"));
            add(new TopItem("M4 SOPMOD II", "https://iopwiki.com/images/thumb/f/f2/M4_SOPMOD_II_S.png/128px-M4_SOPMOD_II_S.png"));
            add(new TopItem("ST AR-15", "https://iopwiki.com/images/thumb/a/a2/ST_AR-15_S.png/128px-ST_AR-15_S.png"));
//            add(new TopItem("AK-47", "https://iopwiki.com/images/thumb/2/22/AK-47_S.png/128px-AK-47_S.png"));
//            add(new TopItem("AK-74U", "https://iopwiki.com/images/thumb/6/6f/AK-74U_S.png/128px-AK-74U_S.png"));
//            add(new TopItem("AS Val", "https://iopwiki.com/images/thumb/1/1f/AS_Val_S.png/128px-AS_Val_S.png"));
//            add(new TopItem("StG44", "https://iopwiki.com/images/thumb/b/bc/StG44_S.png/128px-StG44_S.png"));
//            add(new TopItem("G41", "https://iopwiki.com/images/thumb/a/ab/G41_S.png/128px-G41_S.png"));
//            add(new TopItem("G3", "https://iopwiki.com/images/thumb/7/78/G3_S.png/128px-G3_S.png"));
            add(new TopItem("G36", "https://iopwiki.com/images/thumb/7/7d/G36_S.png/128px-G36_S.png"));
            add(new TopItem("416", "https://iopwiki.com/images/thumb/8/8e/HK416_S.png/128px-HK416_S.png"));
//            add(new TopItem("Type56-1", "https://iopwiki.com/images/thumb/e/e7/Type_56-1_S.png/128px-Type_56-1_S.png"));
//            add(new TopItem("L85A1", "https://iopwiki.com/images/thumb/8/86/L85A1_S.png/128px-L85A1_S.png"));
//            add(new TopItem("FAMAS", "https://iopwiki.com/images/thumb/5/53/FAMAS_S.png/128px-FAMAS_S.png"));
//            add(new TopItem("FNC", "https://iopwiki.com/images/thumb/3/39/FNC_S.png/128px-FNC_S.png"));
//            add(new TopItem("Galil", "https://iopwiki.com/images/thumb/f/f4/Galil_S.png/128px-Galil_S.png"));
//            add(new TopItem("TAR-21", "https://iopwiki.com/images/thumb/b/b3/TAR-21_S.png/128px-TAR-21_S.png"));
//            add(new TopItem("AUG", "https://iopwiki.com/images/thumb/e/e7/AUG_S.png/128px-AUG_S.png"));
//            add(new TopItem("SIG-510", "https://iopwiki.com/images/thumb/8/8e/SIG-510_S.png/128px-SIG-510_S.png"));
//            add(new TopItem("M1918 BAR", "https://iopwiki.com/images/thumb/7/79/M1918_S.png/128px-M1918_S.png"));
//            add(new TopItem("M2HB", "https://iopwiki.com/images/thumb/6/6a/M2HB_S.png/128px-M2HB_S.png"));
//            add(new TopItem("M60", "https://iopwiki.com/images/thumb/4/4d/M60_S.png/128px-M60_S.png"));
            add(new TopItem("M249 SAW", "https://iopwiki.com/images/thumb/3/3a/M249_SAW_S.png/128px-M249_SAW_S.png"));
//            add(new TopItem("M1919A4", "https://iopwiki.com/images/thumb/0/03/M1919A4_S.png/128px-M1919A4_S.png"));
//            add(new TopItem("LWMMG", "https://iopwiki.com/images/thumb/7/75/LWMMG_S.png/128px-LWMMG_S.png"));
//            add(new TopItem("DP28", "https://iopwiki.com/images/thumb/a/a2/DP28_S.png/128px-DP28_S.png"));
//            add(new TopItem("RPD", "https://iopwiki.com/images/thumb/2/26/RPD_S.png/128px-RPD_S.png"));
//            add(new TopItem("PK", "https://iopwiki.com/images/thumb/c/c0/PK_S.png/128px-PK_S.png"));
//            add(new TopItem("MG42", "https://iopwiki.com/images/thumb/e/ef/MG42_S.png/128px-MG42_S.png"));
//            add(new TopItem("MG34", "https://iopwiki.com/images/thumb/5/5a/MG34_S.png/128px-MG34_S.png"));
//            add(new TopItem("MG3", "https://iopwiki.com/images/thumb/8/8c/MG3_S.png/128px-MG3_S.png"));
//            add(new TopItem("Bren", "https://iopwiki.com/images/thumb/e/e9/Bren_S.png/128px-Bren_S.png"));
//            add(new TopItem("FNP9", "https://iopwiki.com/images/thumb/a/aa/FNP-9_S.png/128px-FNP-9_S.png"));
//            add(new TopItem("MP-446", "https://iopwiki.com/images/thumb/c/c4/MP-446_S.png/128px-MP-446_S.png"));
//            add(new TopItem("Spectre-M4", "https://iopwiki.com/images/thumb/a/a0/Spectre_M4_S.png/128px-Spectre_M4_S.png"));
            add(new TopItem("IDW", "https://iopwiki.com/images/thumb/b/bd/IDW_S.png/128px-IDW_S.png"));
//            add(new TopItem("Type64", "https://iopwiki.com/images/thumb/8/8d/Type_64_S.png/128px-Type_64_S.png"));
//            add(new TopItem("Type88", "https://iopwiki.com/images/thumb/0/0b/Hanyang_Type_88_S.png/128px-Hanyang_Type_88_S.png"));
            add(new TopItem("Grizzly", "https://iopwiki.com/images/thumb/2/29/Grizzly_MkV_S.png/128px-Grizzly_MkV_S.png"));
//            add(new TopItem("Calico M950A", "https://iopwiki.com/images/thumb/7/75/M950A_S.png/128px-M950A_S.png"));
//            add(new TopItem("SPP-1", "https://iopwiki.com/images/thumb/b/bb/SPP-1_S.png/128px-SPP-1_S.png"));
//            add(new TopItem("Mk23", "https://iopwiki.com/images/thumb/2/22/Mk23_S.png/128px-Mk23_S.png"));
//            add(new TopItem("P7", "https://iopwiki.com/images/thumb/3/3e/P7_S.png/128px-P7_S.png"));
            add(new TopItem("UMP9", "https://iopwiki.com/images/thumb/f/f9/UMP9_S.png/128px-UMP9_S.png"));
//            add(new TopItem("UMP40", "https://iopwiki.com/images/thumb/f/f6/UMP40_S.png/128px-UMP40_S.png"));
            add(new TopItem("UMP45", "https://iopwiki.com/images/thumb/7/7b/UMP45_S.png/128px-UMP45_S.png"));
//            add(new TopItem("G36C", "https://iopwiki.com/images/thumb/d/da/G36C_S.png/128px-G36C_S.png"));
            add(new TopItem("OTs-12", "https://iopwiki.com/images/thumb/9/97/OTs-12_S.png/128px-OTs-12_S.png"));
            add(new TopItem("FAL", "https://iopwiki.com/images/thumb/3/35/FAL_S.png/128px-FAL_S.png"));
//            add(new TopItem("F2000", "https://iopwiki.com/images/thumb/f/fd/F2000_S.png/128px-F2000_S.png"));
//            add(new TopItem("CZ-805", "https://iopwiki.com/images/thumb/7/7c/CZ-805_S.png/128px-CZ-805_S.png"));
//            add(new TopItem("MG5", "https://iopwiki.com/images/thumb/0/0e/MG5_S.png/128px-MG5_S.png"));
//            add(new TopItem("FG42", "https://iopwiki.com/images/thumb/4/44/FG42_S.png/128px-FG42_S.png"));
//            add(new TopItem("AAT-52", "https://iopwiki.com/images/thumb/a/a6/AAT-52_S.png/128px-AAT-52_S.png"));
            add(new TopItem("Negev", "https://iopwiki.com/images/thumb/9/91/Negev_S.png/128px-Negev_S.png"));
//            add(new TopItem("Serdyukov", "https://iopwiki.com/images/thumb/f/f6/Serdyukov_S.png/128px-Serdyukov_S.png"));
            add(new TopItem("Welrod MKII", "https://iopwiki.com/images/thumb/d/d1/Welrod_MkII_S.png/128px-Welrod_MkII_S.png"));
            add(new TopItem("Suomi", "https://iopwiki.com/images/thumb/c/c4/Suomi_S.png/128px-Suomi_S.png"));
//            add(new TopItem("Z-62", "https://iopwiki.com/images/thumb/1/1c/Z-62_S.png/128px-Z-62_S.png"));
//            add(new TopItem("PSG-1", "https://iopwiki.com/images/thumb/6/65/PSG-1_S.png/128px-PSG-1_S.png"));
            add(new TopItem("9A-91", "https://iopwiki.com/images/thumb/3/30/9A-91_S.png/128px-9A-91_S.png"));
//            add(new TopItem("OTs-14", "https://iopwiki.com/images/thumb/c/cb/OTs-14_S.png/128px-OTs-14_S.png"));
//            add(new TopItem("ARX-160", "https://iopwiki.com/images/thumb/e/ef/ARX-160_S.png/128px-ARX-160_S.png"));
//            add(new TopItem("Mk48", "https://iopwiki.com/images/thumb/c/c2/Mk48_S.png/128px-Mk48_S.png"));
//            add(new TopItem("G11", "https://iopwiki.com/images/thumb/3/3f/G11_S.png/128px-G11_S.png"));
//            add(new TopItem("P99", "https://iopwiki.com/images/thumb/3/36/P99_S.png/128px-P99_S.png"));
            add(new TopItem("Super SASS", "https://iopwiki.com/images/thumb/0/0b/Super_SASS_S.png/128px-Super_SASS_S.png"));
//            add(new TopItem("MG4", "https://iopwiki.com/images/thumb/4/47/MG4_S.png/128px-MG4_S.png"));
//            add(new TopItem("NZ75", "https://iopwiki.com/images/thumb/4/4d/NZ75_S.png/128px-NZ75_S.png"));
//            add(new TopItem("Type 79", "https://iopwiki.com/images/thumb/1/1d/Type_79_S.png/128px-Type_79_S.png"));
//            add(new TopItem("M99", "https://iopwiki.com/images/thumb/7/7c/M99_S.png/128px-M99_S.png"));
//            add(new TopItem("Type95", "https://iopwiki.com/images/thumb/c/c3/Type_95_S.png/128px-Type_95_S.png"));
//            add(new TopItem("Type97", "https://iopwiki.com/images/thumb/1/14/Type_97_S.png/128px-Type_97_S.png"));
//            add(new TopItem("EVO 3", "https://iopwiki.com/images/thumb/3/32/EVO_3_S.png/128px-EVO_3_S.png"));
//            add(new TopItem("Type59", "https://iopwiki.com/images/thumb/a/ac/Type_59_S.png/128px-Type_59_S.png"));
//            add(new TopItem("Type63", "https://iopwiki.com/images/thumb/1/1f/Type_63_S.png/128px-Type_63_S.png"));
//            add(new TopItem("AR70", "https://iopwiki.com/images/thumb/0/04/AR70_S.png/128px-AR70_S.png"));
//            add(new TopItem("SR-3MP", "https://iopwiki.com/images/thumb/1/12/SR-3MP_S.png/128px-SR-3MP_S.png"));
//            add(new TopItem("PP-19", "https://iopwiki.com/images/thumb/0/0b/PP-19_S.png/128px-PP-19_S.png"));
//            add(new TopItem("PP-19-01", "https://iopwiki.com/images/thumb/1/1e/PP-19-01_S.png/128px-PP-19-01_S.png"));
//            add(new TopItem("6P62", "https://iopwiki.com/images/thumb/b/bf/6P62_S.png/128px-6P62_S.png"));
//            add(new TopItem("Bren Ten", "https://iopwiki.com/images/thumb/7/76/Bren_Ten_S.png/128px-Bren_Ten_S.png"));
//            add(new TopItem("PSM", "https://iopwiki.com/images/thumb/e/eb/PSM_S.png/128px-PSM_S.png"));
//            add(new TopItem("USP Compact", "https://iopwiki.com/images/thumb/6/69/USP_Compact_S.png/128px-USP_Compact_S.png"));
//            add(new TopItem("Five-SeveN", "https://iopwiki.com/images/thumb/6/6b/Five-seveN_S.png/128px-Five-seveN_S.png"));
            add(new TopItem("RO635", "https://iopwiki.com/images/thumb/5/5a/RO635_S.png/128px-RO635_S.png"));
//            add(new TopItem("MT-9", "https://iopwiki.com/images/thumb/2/2f/MT-9_S.png/128px-MT-9_S.png"));
//            add(new TopItem("OTs-44", "https://iopwiki.com/images/thumb/b/bf/OTs-44_S.png/128px-OTs-44_S.png"));
//            add(new TopItem("G28", "https://iopwiki.com/images/thumb/9/9c/G28_S.png/128px-G28_S.png"));
//            add(new TopItem("SSG 69", "https://iopwiki.com/images/thumb/f/f5/SSG_69_S.png/128px-SSG_69_S.png"));
            add(new TopItem("IWS-2000", "https://iopwiki.com/images/thumb/2/22/IWS_2000_S.png/128px-IWS_2000_S.png"));
            add(new TopItem("AEK-999", "https://iopwiki.com/images/thumb/4/45/AEK-999_S.png/128px-AEK-999_S.png"));
//            add(new TopItem("Shipka", "https://iopwiki.com/images/thumb/5/54/Shipka_S.png/128px-Shipka_S.png"));
//            add(new TopItem("M1887", "https://iopwiki.com/images/thumb/0/0c/M1887_S.png/128px-M1887_S.png"));
//            add(new TopItem("M1897", "https://iopwiki.com/images/thumb/3/3f/M1897_S.png/128px-M1897_S.png"));
            add(new TopItem("Ithaca M37", "https://iopwiki.com/images/thumb/a/a9/M37_S.png/128px-M37_S.png"));
            add(new TopItem("M500", "https://iopwiki.com/images/thumb/3/30/M500_S.png/128px-M500_S.png"));
            add(new TopItem("M590", "https://iopwiki.com/images/thumb/1/1b/M590_S.png/128px-M590_S.png"));
            add(new TopItem("M870", "https://iopwiki.com/images/3/3d/M870_S.png"));
            add(new TopItem("Super-Shorty", "https://iopwiki.com/images/thumb/3/31/Super-Shorty_S.png/128px-Super-Shorty_S.png"));
//            add(new TopItem("KSG", "https://iopwiki.com/images/thumb/4/41/KSG_S.png/128px-KSG_S.png"));
//            add(new TopItem("KS-23", "https://iopwiki.com/images/thumb/0/09/KS-23_S.png/128px-KS-23_S.png"));
//            add(new TopItem("RMB-93", "https://iopwiki.com/images/thumb/9/9a/RMB-93_S.png/128px-RMB-93_S.png"));
            add(new TopItem("Saiga-12", "https://iopwiki.com/images/thumb/9/99/Saiga-12_S.png/128px-Saiga-12_S.png"));
//            add(new TopItem("Type 97 Shotgun", "https://iopwiki.com/images/thumb/2/20/Type_97_Shotgun_S.png/128px-Type_97_Shotgun_S.png"));
            add(new TopItem("SPAS-12", "https://iopwiki.com/images/thumb/c/ca/SPAS-12_S.png/128px-SPAS-12_S.png"));
            add(new TopItem("AA-12", "https://iopwiki.com/images/thumb/d/d9/AA-12_S.png/128px-AA-12_S.png"));
//            add(new TopItem("FP-6", "https://iopwiki.com/images/thumb/a/a8/FP-6_S.png/128px-FP-6_S.png"));
//            add(new TopItem("M1014", "https://iopwiki.com/images/thumb/b/b5/M1014_S.png/128px-M1014_S.png"));
//            add(new TopItem("CZ75", "https://iopwiki.com/images/thumb/a/a9/CZ75_S.png/128px-CZ75_S.png"));
//            add(new TopItem("Gr HK45", "https://iopwiki.com/images/thumb/7/7e/HK45_S.png/128px-HK45_S.png"));
//            add(new TopItem("Spitfire", "https://iopwiki.com/images/thumb/2/20/Spitfire_S.png/128px-Spitfire_S.png"));
            add(new TopItem("SCW", "https://iopwiki.com/images/thumb/b/bc/SCW_S.png/128px-SCW_S.png"));
//            add(new TopItem("ASh-12.7", "https://iopwiki.com/images/thumb/5/5a/ASh-12.7_S.png/128px-ASh-12.7_S.png"));
//            add(new TopItem("Ribeyrolles", "https://iopwiki.com/images/thumb/b/b5/Ribeyrolles_S.png/128px-Ribeyrolles_S.png"));
            add(new TopItem("RFB", "https://iopwiki.com/images/thumb/e/e9/RFB_S.png/128px-RFB_S.png"));
//            add(new TopItem("PKP", "https://iopwiki.com/images/thumb/5/5c/PKP_S.png/128px-PKP_S.png"));
//            add(new TopItem("Type81R", "https://iopwiki.com/images/thumb/0/03/Type_81_Carbine_S.png/128px-Type_81_Carbine_S.png"));
//            add(new TopItem("ART556", "https://iopwiki.com/images/thumb/6/68/ART556_S.png/128px-ART556_S.png"));
//            add(new TopItem("TMP", "https://iopwiki.com/images/thumb/e/e4/TMP_S.png/128px-TMP_S.png"));
//            add(new TopItem("KLIN", "https://iopwiki.com/images/thumb/5/58/KLIN_S.png/128px-KLIN_S.png"));
//            add(new TopItem("F1", "https://iopwiki.com/images/thumb/a/a1/F1_S.png/128px-F1_S.png"));
//            add(new TopItem("DSR-50", "https://iopwiki.com/images/thumb/a/af/DSR-50_S.png/128px-DSR-50_S.png"));
//            add(new TopItem("PzB39", "https://iopwiki.com/images/thumb/9/9b/PzB_39_S.png/128px-PzB_39_S.png"));
//            add(new TopItem("T91", "https://iopwiki.com/images/thumb/3/35/T91_S.png/128px-T91_S.png"));
//            add(new TopItem("wz.29", "https://iopwiki.com/images/thumb/2/26/wz.29_S.png/128px-wz.29_S.png"));
            add(new TopItem("Contender", "https://iopwiki.com/images/thumb/2/28/Contender_S.png/128px-Contender_S.png"));
            add(new TopItem("T-5000", "https://iopwiki.com/images/thumb/8/87/T-5000_S.png/128px-T-5000_S.png"));
//            add(new TopItem("Ameli", "https://iopwiki.com/images/thumb/9/99/Ameli_S.png/128px-Ameli_S.png"));
//            add(new TopItem("P226", "https://iopwiki.com/images/thumb/b/bb/P226_S.png/128px-P226_S.png"));
//            add(new TopItem("AK5", "https://iopwiki.com/images/thumb/0/06/Ak_5_S.png/128px-Ak_5_S.png"));
            add(new TopItem("S.A.T.8", "https://iopwiki.com/images/thumb/e/e2/S.A.T.8_S.png/128px-S.A.T.8_S.png"));
            add(new TopItem("USAS-12", "https://iopwiki.com/images/thumb/4/49/USAS-12_S.png/128px-USAS-12_S.png"));
//            add(new TopItem("NS2000", "https://iopwiki.com/images/thumb/5/55/NS2000_S.png/128px-NS2000_S.png"));
//            add(new TopItem("M12", "https://iopwiki.com/images/thumb/4/41/M12_S.png/128px-M12_S.png"));
//            add(new TopItem("JS05", "https://iopwiki.com/images/thumb/4/40/JS05_S.png/128px-JS05_S.png"));
//            add(new TopItem("T65", "https://iopwiki.com/images/thumb/a/a8/T65_S.png/128px-T65_S.png"));
            add(new TopItem("K2", "https://iopwiki.com/images/thumb/b/b7/K2_S.png/128px-K2_S.png"));
//            add(new TopItem("MG23", "https://iopwiki.com/images/thumb/5/5a/HK23_S.png/128px-HK23_S.png"));
//            add(new TopItem("Zas M21", "https://iopwiki.com/images/thumb/d/db/Zas_M21_S.png/128px-Zas_M21_S.png"));
//            add(new TopItem("Carcano M1891", "https://iopwiki.com/images/thumb/c/ce/Carcano_M1891_S.png/128px-Carcano_M1891_S.png"));
//            add(new TopItem("Carcano M91/38", "https://iopwiki.com/images/thumb/2/28/Carcano_M91%E2%88%9538_S.png/128px-Carcano_M91%E2%88%9538_S.png"));
//            add(new TopItem("Type 80", "https://iopwiki.com/images/thumb/7/7e/Type_80_S.png/128px-Type_80_S.png"));
//            add(new TopItem("XM3", "https://iopwiki.com/images/thumb/5/5d/XM3_S.png/128px-XM3_S.png"));
//            add(new TopItem("Gepard M1", "https://iopwiki.com/images/thumb/2/23/Gepard_M1_S.png/128px-Gepard_M1_S.png"));
//            add(new TopItem("Thunder", "https://iopwiki.com/images/thumb/8/86/Thunder_S.png/128px-Thunder_S.png"));
//            add(new TopItem("Honey Badger", "https://iopwiki.com/images/thumb/d/d8/Honey_Badger_S.png/128px-Honey_Badger_S.png"));
//            add(new TopItem("Ballista", "https://iopwiki.com/images/thumb/1/14/Ballista_S.png/128px-Ballista_S.png"));
            add(new TopItem("AN-94", "https://iopwiki.com/images/thumb/3/3b/AN-94_S.png/128px-AN-94_S.png"));
            add(new TopItem("AK12", "https://iopwiki.com/images/thumb/c/c2/AK-12_S.png/128px-AK-12_S.png"));
//            add(new TopItem("CZ2000", "https://iopwiki.com/images/thumb/0/01/CZ2000_S.png/128px-CZ2000_S.png"));
//            add(new TopItem("HMG21", "https://iopwiki.com/images/thumb/6/6f/HK21_S.png/128px-HK21_S.png"));
//            add(new TopItem("OTs-39", "https://iopwiki.com/images/thumb/7/73/OTs-39_S.png/128px-OTs-39_S.png"));
//            add(new TopItem("CZ-52", "https://iopwiki.com/images/thumb/5/5c/CZ52_S.png/128px-CZ52_S.png"));
//            add(new TopItem("SRS", "https://iopwiki.com/images/thumb/0/08/SRS_S.png/128px-SRS_S.png"));
            add(new TopItem("K5", "https://iopwiki.com/images/thumb/8/8f/K5_S.png/128px-K5_S.png"));
//            add(new TopItem("C-MS", "https://iopwiki.com/images/thumb/7/70/C-MS_S.png/128px-C-MS_S.png"));
            add(new TopItem("MDR", "https://iopwiki.com/images/thumb/f/f5/MDR_S.png/128px-MDR_S.png"));
//            add(new TopItem("XM8", "https://iopwiki.com/images/thumb/a/ae/XM8_S.png/128px-XM8_S.png"));
//            add(new TopItem("T77", "https://iopwiki.com/images/thumb/f/f2/T77_S.png/128px-T77_S.png"));
//            add(new TopItem("Mk 12", "https://iopwiki.com/images/thumb/7/7c/43M_S.png/128px-43M_S.png"));
//            add(new TopItem("A-91", "https://iopwiki.com/images/thumb/a/ac/MP-443_S.png/128px-MP-443_S.png"));
            add(new TopItem("GSh-18", "https://iopwiki.com/images/thumb/5/5d/GSh-18_S.png/128px-GSh-18_S.png"));
//            add(new TopItem("M870", "https://iopwiki.com/images/thumb/e/e5/TAC-50_S.png/128px-TAC-50_S.png"));
//            add(new TopItem("Model L", "https://iopwiki.com/images/thumb/0/06/Model_L_S.png/128px-Model_L_S.png"));
//            add(new TopItem("M82A1", "https://iopwiki.com/images/thumb/a/a6/PM-06_S.png/128px-PM-06_S.png"));
//            add(new TopItem("MP-448", "https://iopwiki.com/images/thumb/a/a2/Cx4_Storm_S.png/128px-Cx4_Storm_S.png"));
//            add(new TopItem("JS 9", "https://iopwiki.com/images/thumb/5/5b/A-91_S.png/128px-A-91_S.png"));
//            add(new TopItem("100 Shiki", "https://iopwiki.com/images/thumb/1/1b/Type_100_S.png/128px-Type_100_S.png"));
//            add(new TopItem("Mk46", "https://iopwiki.com/images/thumb/c/ce/Px4_Storm_S.png/128px-Px4_Storm_S.png"));
//            add(new TopItem("RT-20", "https://iopwiki.com/images/thumb/8/87/JS_9_S.png/128px-JS_9_S.png"));
//            add(new TopItem("SPR A3G", "https://iopwiki.com/images/thumb/e/ea/SPR_A3G_S.png/128px-SPR_A3G_S.png"));
//            add(new TopItem("TEC-9", "https://iopwiki.com/images/thumb/d/d4/K11_S.png/128px-K11_S.png"));
//            add(new TopItem("SAR-21", "https://iopwiki.com/images/thumb/9/9e/SAR-21_S.png/128px-SAR-21_S.png"));
//            add(new TopItem("QJY-88", "https://iopwiki.com/images/thumb/e/ee/Type_88_S.png/128px-Type_88_S.png"));
//            add(new TopItem("64 Shiki", "https://iopwiki.com/images/thumb/5/5a/Howa_Type_64_S.png/128px-Howa_Type_64_S.png"));
            add(new TopItem("P90", "https://iopwiki.com/images/thumb/3/33/P90_S.png/128px-P90_S.png"));
//            add(new TopItem("K31", "https://iopwiki.com/images/thumb/3/3b/K31_S.png/128px-K31_S.png"));
            add(new TopItem("Jericho", "https://iopwiki.com/images/thumb/9/97/Jericho_S.png/128px-Jericho_S.png"));
//            add(new TopItem("62 Shiki", "https://iopwiki.com/images/thumb/c/cb/Type_62_S.png/128px-Type_62_S.png"));
//            add(new TopItem("Falcon", "https://iopwiki.com/images/thumb/4/4e/Falcon_S.png/128px-Falcon_S.png"));
//            add(new TopItem("M200", "https://iopwiki.com/images/thumb/6/65/M200_S.png/128px-M200_S.png"));
//            add(new TopItem("P22", "https://iopwiki.com/images/thumb/0/08/K3_S.png/128px-K3_S.png"));
//            add(new TopItem("X95", "https://iopwiki.com/images/thumb/e/e8/Desert_Eagle_S.png/128px-Desert_Eagle_S.png"));
//            add(new TopItem("KSVK", "https://iopwiki.com/images/thumb/5/52/SSG_3000_S.png/128px-SSG_3000_S.png"));
//            add(new TopItem("Lewis Gun", "https://iopwiki.com/images/thumb/0/0c/ACR_S.png/128px-ACR_S.png"));
            add(new TopItem("UKM-2000", "https://iopwiki.com/images/thumb/3/32/M1895_CB_S.png/128px-M1895_CB_S.png"));
//            add(new TopItem("HS2000", "https://iopwiki.com/images/thumb/5/53/Kord_S.png/128px-Kord_S.png"));
//            add(new TopItem("Steyr Scout", "https://iopwiki.com/images/thumb/f/ff/VP70_S.png/128px-VP70_S.png"));
//            add(new TopItem("Magal", "https://iopwiki.com/images/thumb/c/c7/MAT-49_S.png/128px-MAT-49_S.png"));
//            add(new TopItem("DP-12", "https://iopwiki.com/images/thumb/d/d4/DP-12_S.png/128px-DP-12_S.png"));
//            add(new TopItem("MG36", "https://iopwiki.com/images/thumb/1/19/Liberator_S.png/128px-Liberator_S.png"));
//            add(new TopItem("EM-2", "https://iopwiki.com/images/thumb/e/ee/Zas_M76_S.png/128px-Zas_M76_S.png"));
//            add(new TopItem("C-93", "https://iopwiki.com/images/thumb/6/6f/C-93_S.png/128px-C-93_S.png"));
//            add(new TopItem("PA-15", "https://iopwiki.com/images/thumb/3/3c/KAC-PDW_S.png/128px-KAC-PDW_S.png"));
//            add(new TopItem("HK33", "https://iopwiki.com/images/thumb/1/12/SIG-556_S.png/128px-SIG-556_S.png"));
//            add(new TopItem("R93", "https://iopwiki.com/images/thumb/0/0a/CR-21_S.png/128px-CR-21_S.png"));
//            add(new TopItem("R5", "https://iopwiki.com/images/thumb/e/e6/R5_S.png/128px-R5_S.png"));
//            add(new TopItem("Howa 89", "https://iopwiki.com/images/thumb/7/7b/Howa_Type_89_S.png/128px-Howa_Type_89_S.png"));
            add(new TopItem("RPK-16", "https://iopwiki.com/images/thumb/7/7c/43M_S.png/128px-43M_S.png"));
            add(new TopItem("AK-15", "https://iopwiki.com/images/thumb/1/13/AK-15_S.png/128px-AK-15_S.png"));
//            add(new TopItem("Webley", "https://iopwiki.com/images/thumb/3/35/Webley_S.png/128px-Webley_S.png"));
//            add(new TopItem("CF05", "https://iopwiki.com/images/thumb/e/e9/CF05_S.png/128px-CF05_S.png"));
//            add(new TopItem("SL8", "https://iopwiki.com/images/thumb/0/08/SL8_S.png/128px-SL8_S.png"));
//            add(new TopItem("M82", "https://iopwiki.com/images/thumb/1/16/M82_S.png/128px-M82_S.png"));
//            add(new TopItem("4 Shiki", "https://iopwiki.com/images/thumb/a/ac/HSM10_S.png/128px-HSM10_S.png"));
//            add(new TopItem("P30", "https://iopwiki.com/images/thumb/4/43/CAR_S.png/128px-CAR_S.png"));
//            add(new TopItem("ADS", "https://iopwiki.com/images/thumb/b/b0/MAS-38_S.png/128px-MAS-38_S.png"));
//            add(new TopItem("T-CMS", "https://iopwiki.com/images/thumb/1/18/Defender_S.png/128px-Defender_S.png"));
//            add(new TopItem("HP-35", "https://iopwiki.com/images/thumb/5/58/HP-35_S.png/128px-HP-35_S.png"));
//            add(new TopItem("SAF", "https://iopwiki.com/images/thumb/a/a9/SAF_S.png/128px-SAF_S.png"));
//            add(new TopItem("Tabuk", "https://iopwiki.com/images/thumb/3/3d/Tabuk_S.png/128px-Tabuk_S.png"));
//            add(new TopItem("K3", "https://iopwiki.com/images/thumb/7/79/AK-Alfa_S.png/128px-AK-Alfa_S.png"));
//            add(new TopItem("ZB-26", "https://iopwiki.com/images/thumb/a/aa/ZB-26_S.png/128px-ZB-26_S.png"));
//            add(new TopItem("M1895 CB", "https://iopwiki.com/images/thumb/8/89/Rex_Zero_1_S.png/128px-Rex_Zero_1_S.png"));
//            add(new TopItem("StG-940", "https://iopwiki.com/images/thumb/1/13/StG-940_S.png/128px-StG-940_S.png"));
//            add(new TopItem("TS12", "https://iopwiki.com/images/thumb/7/72/TS12_S.png/128px-TS12_S.png"));
//            add(new TopItem("Noel Vermillion", "https://iopwiki.com/images/thumb/9/9c/Noel_S.png/128px-Noel_S.png"));
//            add(new TopItem("Elphelt", "https://iopwiki.com/images/thumb/a/a8/Elphelt_S.png/128px-Elphelt_S.png"));
            add(new TopItem("El Clear", "https://iopwiki.com/images/thumb/a/a1/Clear_S.png/128px-Clear_S.png"));
            add(new TopItem("El Fail", "https://iopwiki.com/images/thumb/b/be/Fail_S.png/128px-Fail_S.png"));
//            add(new TopItem("Jill Stingray", "https://iopwiki.com/images/thumb/0/00/Jill_S.png/128px-Jill_S.png"));
//            add(new TopItem("Sei Asagiri", "https://iopwiki.com/images/thumb/9/96/Sei_S.png/128px-Sei_S.png"));
//            add(new TopItem("Dorothy Haze", "https://iopwiki.com/images/thumb/3/32/Dorothy_S.png/128px-Dorothy_S.png"));
//            add(new TopItem("Stella Hoshii", "https://iopwiki.com/images/thumb/3/33/Stella_S.png/128px-Stella_S.png"));
//            add(new TopItem("Alma Armas", "https://iopwiki.com/images/thumb/5/55/Alma_S.png/128px-Alma_S.png"));
//            add(new TopItem("Dana Zane", "https://iopwiki.com/images/thumb/1/1b/Dana_S.png/128px-Dana_S.png"));
//            add(new TopItem("Henrietta", "https://iopwiki.com/images/thumb/2/29/Henrietta_S.png/128px-Henrietta_S.png"));
//            add(new TopItem("Rico", "https://iopwiki.com/images/thumb/2/2c/Rico_S.png/128px-Rico_S.png"));
//            add(new TopItem("Triela", "https://iopwiki.com/images/thumb/f/f9/Triela_S.png/128px-Triela_S.png"));
//            add(new TopItem("Claes", "https://iopwiki.com/images/thumb/f/ff/Claes_S.png/128px-Claes_S.png"));
//            add(new TopItem("Angelica", "https://iopwiki.com/images/thumb/0/0d/Angelica_S.png/128px-Angelica_S.png"));
//            add(new TopItem("Agent Vector", "https://iopwiki.com/images/thumb/6/64/Agent_Vector_S.png/128px-Agent_Vector_S.png"));
//            add(new TopItem("W - April Fools", "https://iopwiki.com/images/thumb/7/7c/43M_S.png/128px-43M_S.png"));
        }};
    }
}