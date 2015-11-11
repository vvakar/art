package vvakar;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by valentin.vakar on 11/11/15.
 */
public class ArtTest {
    @Test(expected =  IllegalArgumentException.class)
    public void testNameNotNull() {
        new Art(null, ArtType.PAINTING, null, "val", new Date());
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testTypeNotNull() {
        new Art("bla", null, null, "val", new Date());
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testArtistNotNull() {
        new Art("bla", ArtType.PAINTING, null, null, new Date());
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testDateNotNull() {
        new Art("bla", ArtType.PAINTING, null, "val", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPriceNotNegative() {
        new Art("bla", ArtType.PAINTING, -1, "val", new Date());
    }

    @Test
    public void testImmutableDate() {
        final long now = System.currentTimeMillis();
        Art art = new Art("bla", ArtType.PAINTING, null, "val", new Date(now));
        art.getCreated().setTime(now - 100L);
        assertEquals(now, art.getCreated().getTime());
    }

    @Test
    public void testEqualsOnNameTypeArtist() {
        Art first = new Art("bla", ArtType.PAINTING, 33, "val",  new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        Art second = new Art("bla", ArtType.PAINTING, 11, "val",  new GregorianCalendar(2010, Calendar.AUGUST, 1).getTime());
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void testDifferentOnName() {
        Art first = new Art("bla", ArtType.PAINTING, 33, "val",  new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        Art second = new Art("differentBla", ArtType.PAINTING, 11, "val",  new GregorianCalendar(2010, Calendar.AUGUST, 1).getTime());
        assertFalse(first.equals(second));
    }

    @Test
    public void testDifferentOnType() {
        Art first = new Art("bla", ArtType.PAINTING, 33, "val",  new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        Art second = new Art("bla", ArtType.TAPESTRY, 11, "val",  new GregorianCalendar(2010, Calendar.AUGUST, 1).getTime());
        assertFalse(first.equals(second));
    }

    @Test
    public void testDifferentOnArtist() {
        Art first = new Art("bla", ArtType.PAINTING, 33, "val",  new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        Art second = new Art("bla", ArtType.PAINTING, 11, "michelangelo",  new GregorianCalendar(2010, Calendar.AUGUST, 1).getTime());
        assertFalse(first.equals(second));
    }

}
