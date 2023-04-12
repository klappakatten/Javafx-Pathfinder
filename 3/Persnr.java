import java.util.Objects;

public class Persnr {
	private int fdat, nr;

	public Persnr(int fdat, int nr) {
		this.fdat = fdat;
		this.nr = nr;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Persnr) {
			return fdat == ((Persnr) o).fdat && nr == ((Persnr) o).nr;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fdat, nr);
	}

	public int getÅr() {
		return fdat / 10000;
	}

	public int getMånad() {
		return fdat / 100 % 100;
	}

	public int getDag() {
		return fdat % 100;
	}

	public int getNr() {
		return nr;
	}

	public String toString() {
		return fdat + "-" + nr;
	}

	public static Persnr parsePersnr(String str) {
		int pos = str.indexOf("-");
		int fd = Integer.parseInt(str.substring(0, pos));
		int nr = Integer.parseInt(str.substring(pos + 1));
		return new Persnr(fd, nr);
	}

}
