package vvakar;

import java.util.Date;
import java.util.Optional;

/**
 * Information about a piece of artwork, such as a painting.
 *
 * For brevity, use immutable public fields instead of getters.
 */
public final class Art {
    public final String name;
    public final ArtType type;
    public final Optional<Integer> pricePence;
    public final String artist;
    private final Date created;

    /**
     * Instantiate an immutable piece of {@code Art}
     *
     * @param name e.g. "Mona Lisa". Must be non-null.
     * @param type type of art as per {@code ArtType}. Must be non-null.
     * @param pricePence optional price in pence or null
     * @param artist e.g. "Leonardo da Vinci". Must be non-null.
     * @param created date artwork created. Must be non-null.
     */
    public Art(String name, ArtType type, Integer pricePence, String artist, Date created) {
        if(name == null) throw new IllegalArgumentException("Name cannot be null");
        if(type == null) throw new IllegalArgumentException("Type cannot be null");
        if(artist == null) throw new IllegalArgumentException("Artist cannot be null");
        if(created == null) throw new IllegalArgumentException("Created cannot be null");
        if(pricePence != null && pricePence < 0) throw new IllegalArgumentException("Price cannot be negative");

        this.name = name;
        this.type = type;
        this.artist = artist;
        this.created = new Date(created.getTime());
        this.pricePence = Optional.ofNullable(pricePence);
    }

    /**
     * Alas - {@code Date} is mutable
     * @return
     */
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Art art = (Art) o;

        if (!name.equals(art.name)) return false;
        if (type != art.type) return false;
        return artist.equals(art.artist);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + artist.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Art{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", pricePence=" + pricePence +
                ", artist='" + artist + '\'' +
                ", created=" + created +
                '}';
    }
}
