package com.example.realstatemanagers;


import com.example.realstatemanagers.repository.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Test
    public void getVerifConvertDtoE() {
        int convertDtoE = Utils.convertDollarToEuro(100);
        assertEquals(94, convertDtoE);
    }

    @Test
    public void getVerifConvertEtoD() {

        int convertDtoE = Utils.convertEuroToDollars(1001);
        assertEquals(1001, convertDtoE);
    }

    @Test
    public void getDateFormatNormal() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());

        String dateFunction = Utils.getTodayDate();
        assertEquals(date, dateFunction);
    }
}
