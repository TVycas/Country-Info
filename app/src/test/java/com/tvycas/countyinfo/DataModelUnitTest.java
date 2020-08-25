package com.tvycas.countyinfo;


import androidx.test.filters.SmallTest;

import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryInfo;
import com.tvycas.countyinfo.model.Currency;
import com.tvycas.countyinfo.model.Language;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@SmallTest
public class DataModelUnitTest {

    @Test
    public void countryBase_singleDigitFormat() {
        CountryBase countryBase = new CountryBase("Test", "Tests", 1);
        String correctOutput = "1";
        assertEquals(countryBase.formatPopulation(), correctOutput);
    }

    @Test
    public void countryBase_manyDigitsFormat() {
        CountryBase countryBase = new CountryBase("Test", "Tests", 1234567890);
        String correctOutput = "1,234,567,890";
        assertEquals(correctOutput, countryBase.formatPopulation());
    }

    @Test
    public void countryInfo_singleLanguageFormat() {
        ArrayList<Language> singleLanguage = new ArrayList<>();
        singleLanguage.add(new Language("TestLang1"));
        CountryInfo countryInfo = createCountryInfoWithLangsArrayList(singleLanguage);

        String correctOutput = "TestLang1";
        assertEquals(correctOutput, countryInfo.constructLangsString());
    }

    @Test
    public void countryInfo_3LanguageFormat() {
        ArrayList<Language> threeLanguages = new ArrayList<>();
        threeLanguages.add(new Language("TestLang1"));
        threeLanguages.add(new Language("TestLang2"));
        threeLanguages.add(new Language("TestLang3"));

        CountryInfo countryInfo = createCountryInfoWithLangsArrayList(threeLanguages);

        String correctOutput = "TestLang1, TestLang2, TestLang3";
        assertEquals(correctOutput, countryInfo.constructLangsString());
    }

    private CountryInfo createCountryInfoWithLangsArrayList(ArrayList<Language> langs) {
        return new CountryInfo(new ArrayList<Currency>() {{
            add(null);
        }}, langs, "Test", "TTT", "Tests", 1000, "Testing");
    }
}
