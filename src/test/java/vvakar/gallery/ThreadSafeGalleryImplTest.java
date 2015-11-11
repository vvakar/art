package vvakar.gallery;

import org.junit.Before;
import org.junit.Test;
import vvakar.Art;
import vvakar.ArtType;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class ThreadSafeGalleryImplTest {

    private Gallery gallery;
    private final static Art ART_FIXTURE = new Art("bla", ArtType.PAINTING, 33, "michelangelo", new Date());

    @Before
    public void before() {
        gallery = new ThreadSafeGalleryImpl();
    }

    @Test
    public void testaddArt() {
        assertNull(gallery.addArt(ART_FIXTURE)); // first time empty
        assertEquals(ART_FIXTURE, gallery.addArt(ART_FIXTURE)); // second time replace and return
    }

    @Test
    public void testDeleteArt_empty() {
        assertNull(gallery.deleteArt(ART_FIXTURE));
    }

    @Test
    public void testDeleteArt() {
        gallery.addArt(ART_FIXTURE);
        assertEquals(ART_FIXTURE, gallery.deleteArt(ART_FIXTURE)); // first time deletes
        assertNull(gallery.deleteArt(ART_FIXTURE)); // second time not found
    }

    @Test
    public void testGetAllArt_empty() {
        assertEquals(Collections.EMPTY_SET, gallery.getAllArt());
    }

    @Test
    public void testGetAllArt() {
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE), gallery.getAllArt());
    }

    @Test
    public void testGetArtists_empty() {
        assertEquals(Collections.EMPTY_SET, gallery.getArtists());

    }

    @Test
    public void testGetArtists() {
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE.artist), gallery.getArtists());
    }

    @Test
    public void testGetArtByArtist_empty() {
        assertEquals(Collections.EMPTY_SET, gallery.getArtByArtist(ART_FIXTURE.artist));
    }

    @Test
    public void testGetArtByArtist() {
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE), gallery.getArtByArtist(ART_FIXTURE.artist));
    }

    @Test
    public void testGetNothingForUnknownArtist() {
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.EMPTY_SET, gallery.getArtByArtist("Someone Unknown"));
    }

    @Test
    public void getRecentArt_empty() {
        assertEquals(Collections.EMPTY_SET, gallery.getRecentArt());
    }

    @Test
    public void getRecentArt_tooOld() {
        final long APPROX_ONE_YEAR_MILLIS = 1000 * 60 * 60 * 24 * 365; // TODO: use JodaTime
        Date looongAgo = new Date(System.currentTimeMillis() - APPROX_ONE_YEAR_MILLIS * 100);
        gallery.addArt(new Art("bla", ArtType.PAINTING, 1, "val", looongAgo));
        assertEquals(Collections.EMPTY_SET, gallery.getRecentArt());
    }

    @Test
    public void getRecentArt_match() {
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE), gallery.getRecentArt());
    }

    @Test
    public void getArtByPrice_empty() {
        assertEquals(Collections.EMPTY_SET, gallery.getArtByPrice(Optional.empty(), Optional.empty()));
    }

    @Test
    public void getArtByPrice_priceless() {
        gallery.addArt(new Art("bla", ArtType.PAINTING, null, "val", new Date()));
        assertEquals(Collections.EMPTY_SET, gallery.getArtByPrice(Optional.empty(), Optional.empty()));
    }

    @Test
    public void getArtByPrice_lowerBoundNotFound() {
        int lowerBoundTooHigh = ART_FIXTURE.pricePence.get() + 1;
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.EMPTY_SET, gallery.getArtByPrice(Optional.of(lowerBoundTooHigh), Optional.empty()));
    }

    @Test
    public void getArtByPrice_lowerBoundInRange() {
        int lowerBound = ART_FIXTURE.pricePence.get();
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE), gallery.getArtByPrice(Optional.of(lowerBound), Optional.empty()));
    }

    @Test
    public void getArtByPrice_upperBoundTooLow() {
        int upperBoundTooLow = ART_FIXTURE.pricePence.get() - 1;
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.EMPTY_SET, gallery.getArtByPrice(Optional.empty(), Optional.of(upperBoundTooLow)));
    }

    @Test
    public void getArtByPrice_upperBoundInRange() {
        int upperBound = ART_FIXTURE.pricePence.get();
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE), gallery.getArtByPrice(Optional.empty(), Optional.of(upperBound)));
    }

    @Test
    public void getArtByPrice_upperAndLowerBoundOutOfRange() {
        int upperBound = ART_FIXTURE.pricePence.get() - 1;
        int lowerBound = ART_FIXTURE.pricePence.get() - 2;
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.EMPTY_SET, gallery.getArtByPrice(Optional.of(lowerBound), Optional.of(upperBound)));
    }

    @Test
    public void getArtByPrice_upperAndLowerBoundInRange() {
        int upperBound = ART_FIXTURE.pricePence.get();
        int lowerBound = ART_FIXTURE.pricePence.get();
        gallery.addArt(ART_FIXTURE);
        assertEquals(Collections.singleton(ART_FIXTURE), gallery.getArtByPrice(Optional.of(lowerBound), Optional.of(upperBound)));
    }
}
