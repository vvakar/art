package vvakar.gallery;

import vvakar.Art;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * A thread-safe {@code Gallery}
 */
public class ThreadSafeGalleryImpl implements Gallery {
    private static final long APPROX_ONE_YEAR_MILLIS = 1000 * 60 * 60 * 24 * 365; // TODO: use JodaTime for leap year stuff
    private final Map<Art, Art> artObjects = new ConcurrentHashMap<>();

    public Art addArt(Art toAdd) {
        return artObjects.put(toAdd, toAdd);
    }

    /**
     * Remove a specific piece of art from the gallery.
     *
     * @param toRemove art to remove
     * @return removed art if any, otherwise null
     */
    @Override
    public Art deleteArt(Art toRemove) {
        return artObjects.remove(toRemove);
    }

    /**
     * Returns all art currently in the gallery
     */
    @Override
    public Collection<Art> getAllArt() {
        return artObjects.keySet();
    }

    /**
     * Returns the names of all artists with art currently in the gallery, in alphabetic order
     */
    @Override
    public Collection<String> getArtists() {
        return artObjects.keySet().stream().map(a -> a.artist).collect(Collectors.toSet());
    }

    /**
     * Returns all art by a specific artist
     */
    @Override
    public Collection<Art> getArtByArtist(String artist) {
        return artObjects.keySet().stream().filter(a -> a.artist.equals(artist)).collect(Collectors.toSet());
    }

    /**
     * Returns all art with a creation date in the past year
     */
    @Override
    public Collection<Art> getRecentArt() {
        // TODO: Use JodaTime for easier leap year math
        final Date yearAgo = new Date();
        yearAgo.setTime(System.currentTimeMillis() - APPROX_ONE_YEAR_MILLIS);
        return artObjects.keySet().stream().filter(a -> a.getCreated().after(yearAgo)).collect(Collectors.toSet());
    }

    /**
     * Returns all art between an upper and a lower price limit. Both limits should be optional. Ignore art with no asking price.
     *
     * @param from lower bound inclusively, if any
     * @param to   upper bound inclusively, if any
     */
    @Override
    public Collection<Art> getArtByPrice(Optional<Integer> from, Optional<Integer> to) {
        return artObjects.keySet().stream()
                .filter(a -> a.pricePence.isPresent()) // filter out priceless art
                .filter(a -> !from.isPresent() || a.pricePence.get() >= from.get()) // check lower bound if any
                .filter(a -> !to.isPresent() || a.pricePence.get() <= to.get()) // check upper bound if any
                .collect(Collectors.toSet());
    }
}
