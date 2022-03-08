package base;

import java.util.Date;

public class Note implements Comparable<Note>{
	
	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Note other = (Note) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(Note o) {
		if (this.date.before(o.date))
			return 1;
		else if (this.date.after(o.date))
			return -1;
		else
			return 0;
	}
	
	public String toString() {
		return date.toString() + "\t" + title;
	}
}
