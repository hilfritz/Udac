package com.hilfritz.musicplayercountdowntimer;

import junit.framework.TestCase;

/**
 * @author hilfritz.p.camallere on 6/19/2015.
 */
public class TimeFormatUtilTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testGetFormattedDate() throws Exception {

        String a = TimeFormatUtil.getFormattedDateForDisplay(3000, TimeFormatUtil.MIN_SEC_FORMAT);
        assertEquals("00:03", a);

        String b = TimeFormatUtil.getFormattedDateForDisplay(240000, TimeFormatUtil.MIN_SEC_FORMAT);
        assertTrue(b.equals("04:00"));

        String c = TimeFormatUtil.getFormattedDateForDisplay(245000, TimeFormatUtil.MIN_SEC_FORMAT);
        assertTrue(c.equals("04:05"));

        String d = TimeFormatUtil.getFormattedDateForDisplay(245000, TimeFormatUtil.HR_MIN_SEC_FORMAT);
        assertNotNull(d);
        assertEquals(d,"08:04:05");
    }
}