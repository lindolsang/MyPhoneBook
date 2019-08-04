package kr.lindol.android.myphonebook.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneNumberModelTest {

    private PhoneNumberModel model;

    @Before
    public void setUp() {
        model = new PhoneNumberModel();
    }

    @Test
    public void addWithPhoneNumberThenReturnTrue() {
        boolean result = model.add("01064078239", "Juyeon");
        assertTrue(result);
    }

    @Test
    public void addWithPhoneNumberThenReturnFalse() {
        boolean result = model.add("01064078239", "Juyeon");
        assertTrue(result);

        result = model.add("01064078239", "Juyeon");
        assertFalse(result);
    }

    @Test
    public void containsWithRegisteredPhoneNumberThenReturnTrue() {
        model.add("01064078239", "Juyeon");

        boolean result = model.contains("01064078239");
        assertTrue(result);
    }

    @Test
    public void containsWithNotRegisteredPhoneNumberThenReturnFalse() {

        model.add("01064078239", "Juyeon");

        boolean result = model.contains("01064078238");

        assertFalse(result);
    }

    @Test
    public void getWithRegisteredPhoneNumberThenReturnExtraData() {
        model.add("01064078239", "Martin");
        model.add("01095925678", "John");

        assertEquals("Martin", model.get("01064078239"));
        assertEquals("John", model.get("01095925678"));
    }

    @Test
    public void getWithNotRegisteredNumberThenReturnEmpty() {
        model.add("01011112222", "Jim");
        model.add("01022221111", "David");

        assertEquals("", model.get("01011111111"));
        assertEquals("", model.get("01122221111"));
    }
}