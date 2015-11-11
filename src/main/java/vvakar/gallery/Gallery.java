package vvakar.gallery;

import vvakar.Art;

import java.util.Collection;
import java.util.Optional;

/**
 * A service that maintains a collection of Art.
 */
public interface Gallery {
    /**
     * Add a piece of art to the gallery, replacing any existing equivalent piece
     *
     * @param toAdd art to add
     * @return replaced equivalent piece or null
     */
    public Art addArt(Art toAdd);

    /**
     * Remove a specific piece of art from the gallery.
     *
     * @param toRemove art to remove
     * @return removed art if any, otherwise null
     */
    public Art deleteArt(Art toRemove);

    /**
     * Returns all art currently in the gallery
     */
    public Collection<Art> getAllArt();

    /**
     * Returns the names of all artists with art currently in the gallery, in alphabetic order
     */
    public Collection<String> getArtists();

    /**
     * Returns all art by a specific artist
     */
    public Collection<Art> getArtByArtist(String artist);

    /**
     * Returns all art with a creation date in the past year
     */
    public Collection<Art> getRecentArt();

    /**
     * Returns all art between an upper and a lower price limit. Both limits should be optional. Ignore art with no asking price.
     *
     * @param from lower bound inclusively, if any
     * @param to   upper bound inclusively, if any
     */
    public Collection<Art> getArtByPrice(Optional<Integer> from, Optional<Integer> to);


}
