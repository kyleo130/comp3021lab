package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Folder implements Comparable<Folder>{
	
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}

	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for (Note note : notes) {
			if (note instanceof TextNote) {
				nText += 1;
			}
			else if (note instanceof ImageNote) {
				nImage += 1;
			}
		}
		
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Folder o) {
		if (this.name.compareTo(o.name) < 0)
			return -1;
		else if (this.name.compareTo(o.name) > 0)
			return 1;
		else
			return 0;
	}
	
	public void sortNotes() {
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords) {
		
		// "word1 word2 or word3 word4" -> ["word1", "word2", "or", "word3", "word4"]
		String tokens[] = keywords.split(" ");
		
		ArrayList<ArrayList<String>> searchKeys = new ArrayList<ArrayList<String>>();
		
		if (tokens.length > 0) {
			
			ArrayList<String> orTokens = new ArrayList<String>();
			
			// ["word1", "word2", "or", "word3", "word4"] -> [["word1"], ["word2", "word3"], ["word4"]]
			for (int i = 0; i < tokens.length - 1; i++) {
				if (tokens[i].equalsIgnoreCase("or") == true) {
					continue;
				}
				
				orTokens.add(tokens[i]);
				
				if (tokens[i+1].equalsIgnoreCase("or") == false) {
					searchKeys.add(orTokens);
					orTokens = new ArrayList<String>();
				}
			}
			
			orTokens.add(tokens[tokens.length - 1]);
			searchKeys.add(orTokens);
		}
		
		List<Note> noteList = new ArrayList<Note>();
		
		for (Note note : this.notes) {
			String noteTitle = note.getTitle();
			String noteContent = null;
			
			if (note instanceof TextNote) {
				noteContent = ((TextNote) note).content;
			}
			
			boolean matchAnd = true;
			
			for (ArrayList<String> orTokens : searchKeys) {
				boolean matchOr = false;
				
				for (String word : orTokens) {
					if (noteTitle.toLowerCase().contains(word.toLowerCase()) == true) {
						matchOr = true;
						break;
					}
					
					if (note instanceof TextNote) {
						if (noteContent.toLowerCase().contains(word.toLowerCase()) == true) {
							matchOr = true;
							break;
						}
					}
				}
				
				if (matchOr == false) {
					matchAnd = false;
					break;
				}
			}
			
			if (matchAnd == true) {
				noteList.add(note);
			}
		}
		
		return noteList;
	}
}
