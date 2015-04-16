package cz.mzk.recordmanager.server.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.ISBNValidator;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;

import cz.mzk.recordmanager.server.export.IOFormat;
import cz.mzk.recordmanager.server.marc.MarcRecord;
import cz.mzk.recordmanager.server.model.HarvestedRecordFormat;
import cz.mzk.recordmanager.server.util.MetadataUtils;

public class MetadataMarcRecord implements MetadataRecord {

	protected MarcRecord underlayingMarc;
	
	protected final ISBNValidator isbnValidator = ISBNValidator.getInstance(true);

	protected static final Pattern PAGECOUNT_PATTERN = Pattern.compile("\\d+");
	protected static final Pattern YEAR_PATTERN = Pattern.compile("\\d{4}");
	
	public MetadataMarcRecord(MarcRecord underlayingMarc) {
		if (underlayingMarc == null) {
			throw new IllegalArgumentException("Creating MetadataMarcRecord with NULL underlayingMarc.");
		}
		this.underlayingMarc = underlayingMarc;
	}
	
	@Override
	public String getUniqueId() {
		// TODO override this implementation in institution specific classes
		String id = underlayingMarc.getControlField("001");
		if (id == null) {
			id = underlayingMarc.getField("995", 'a');
		}
		return id;
	}

	@Override
	public List<String> getISSNs() {	
		List<String> issns = new ArrayList<String>();
		
		for(String issn: underlayingMarc.getFields("022", 'a')){
			issns.add(issn.replace("-", ""));
		}
		
		return issns;
		
	}

	@Override
	public String getSeriesISSN() {	
		return underlayingMarc.getField("490", 'x');
	}

	@Override
	public Long getPageCount() {		
		String count = underlayingMarc.getField("300", 'a');
		if(count == null){
			return null;
		}	
		
		Matcher matcher = PAGECOUNT_PATTERN.matcher(count);
		try {
			if (matcher.find()) {
				return Long.parseLong(matcher.group(0));
			}
		} catch (NumberFormatException e) {}
		return null;
	}
	
	@Override
	public List<String> getISBNs() {
		List<String> isbns = new ArrayList<String>();
		
		for(String isbn: underlayingMarc.getFields("020", 'a')){	
			isbn = isbnValidator.validate(isbn);
			if(isbn == null) continue;
			if(!isbns.contains(isbn)) isbns.add(isbn);
		}
		
		return isbns;
	}
	
	public String getFormat() {
		boolean onlineResource = false;
		List<ControlField> cfl = underlayingMarc.getControlFields("007");
				
		for (ControlField field : cfl) {
			String data = field.getData();
			if (data.length() < 2) {
				continue;
			}
			char code1 = Character.toUpperCase(data.charAt(0));
			char code2 = Character.toUpperCase(data.charAt(1));

			switch (code1) {
			case 'A':
				switch (code2) {
				case 'D':
					return "Atlas";
				default:
					return "Map";
				}
			case 'C':
				switch (code2) {
				case 'A':
					return "TapeCartridge";
				case 'B':
					return "ChipCartridge";
				case 'C':
					return "DiscCartridge";
				case 'F':
					return "TapeCassette";
				case 'H':
					return "TapeReel";
				case 'J':
					return "FloppyDisk";
				case 'M':
				case 'O':
					return "CDROM";
				case 'R':
					// Do not return - this will cause anything with an
					// 856 field to be labeled as "Electronic"
					onlineResource = true;
					break;
				default:
					return "Electronic";
				}
				break;
			case 'D':
				return "Globe";
			case 'F':
				return "Braille";
			case 'G':
				switch (code2) {
				case 'C':
				case 'D':
					return "Filmstrip";
				case 'T':
					return "Transparency";
				default:
					return "Slide";
				}
			case 'H':
				return "Microfilm";
			case 'K':
				switch (code2) {
				case 'C':
					return "Collage";
				case 'D':
					return "Drawing";
				case 'E':
					return "Painting";
				case 'F':
					return "Print";
				case 'G':
					return "Photonegative";
				case 'J':
					return "Print";
				case 'L':
					return "TechnicalDrawing";
				case 'O':
					return "FlashCard";
				case 'N':
					return "Chart";
				default:
					return "Photo";
				}
			case 'M':
				switch (code2) {
				case 'F':
					return "VideoCassette";
				case 'R':
					return "Filmstrip";
				default:
					return "MotionPicture";
				}
			case 'O':
				return "Kit";
			case 'Q':
				return "MusicalScore";
			case 'R':
				return "SensorImage";
			case 'S':
				switch (code2) {
				case 'D':
					if (data.length() > 14) {
						return Character.toUpperCase(data.charAt(13)) == 'D' ? "CD" : "SoundDisc";
					}
					break;
				case 'S':
					return "SoundCassette";
				default:
					return "SoundRecording";
				}
			case 'V':
				if (data.length() < 5) {
					break;
				}
				switch (Character.toUpperCase(data.charAt(4))) {
				case 'S':
					return "BluRay";
				case 'V':
					return "DVD";
				}
				switch (code2) {
				case 'C':
					return "VideoCartridge";
				case 'D':
					return "VideoDisc";
				case 'F':
					return "VideoCassette";
				case 'R':
					return "VideoReel";
				default:
					return "Video";
				}
			}
		}

		char leaderCode = underlayingMarc.getLeader().getTypeOfRecord();
		
		switch (Character.toUpperCase(leaderCode)) {
		case 'C':
		case 'D':
			return "MusicalScore";
		case 'E':
		case 'F':
			return "Map";
		case 'G':
			return "Slide";
		case 'I':
			return "SoundRecording";
		case 'J':
			return "MusicRecording";
		case 'K':
			return "Photo";
		case 'M':
			return "Electronic";
		case 'O':
		case 'P':
			return "Kit";
		case 'R':
			return "PhysicalObject";
		case 'T':
			return "Manuscript";
		}
		
		leaderCode = underlayingMarc.getLeader().getImplDefined1()[0];
		
		switch (Character.toUpperCase(leaderCode)) {
		// Monograph
		case 'M':
			if (onlineResource) {
				return "eBook";
			} else {
				return "Book";
			}
		// Serial
		case 'S':
			// Look in 008 to determine what type of Continuing Resource
			List<ControlField> innerCfl = underlayingMarc.getControlFields("008");		
			for (ControlField innerField : innerCfl) {
				if (innerField.getData().length() < 23) {
					continue;
				}
				char innerCode = innerField.getData().charAt(22);
				switch (Character.toUpperCase(innerCode)) {
				case 'N':
					return onlineResource ? "eNewspaper" : "Newspaper";
				case 'P':
					return onlineResource ? "eJournal" : "Journal";
				default:
					return onlineResource ? "eSerial" : "Serial";
				}
			}
			break;
		case 'A':
			// Component part in monograph
			return onlineResource ? "eBookSection" : "BookSection";
		case 'B':
			// Component part in serial
			return onlineResource ? "eArticle" : "Article";
		case 'C':
			// Collection
			return "Collection";
		case 'D':
			// Component part in collection (sub unit)
			return "SubUnit";
		case 'I':
			// Integrating resource
			return "ContinuouslyUpdatedResource";
		}
		return "Other";
			
	}

	@Override
	public Long getPublicationYear() {
		String year = underlayingMarc.getField("260", 'c');
		if (year == null) {
			return null;
		}
		Matcher matcher = YEAR_PATTERN.matcher(year);
		try {
			if (matcher.find()) {
				return Long.parseLong(matcher.group(0));
			}
		} catch (NumberFormatException e) {}
		return null;
	}
		
	/**
	 * @return 245a:245b.245n.245p
	 */
	@Override
	public String getTitle() {
		final char titleSubfields[] = new char[]{'a','b','n','p'};
		final char punctiation[] = new char[]{':', '.', '.' };
		final DataField field = underlayingMarc.getDataFields("245").get(0);
		final List<Subfield> subfields = underlayingMarc.getSubfields(field, titleSubfields);

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < titleSubfields.length; i++) {
			if (i >= subfields.size() || subfields.get(i).getCode() != titleSubfields[i]) {
				return builder.toString();
			}
			if (i > 0) {
				if (MetadataUtils.hasTrailingPunctuation(builder.toString())) {
					builder.append(" ");
				} else {
					builder.append(punctiation[i-1]);
				}
			}
			builder.append(subfields.get(i).getData());
		}
		return builder.toString();
	}

	@Override
	public String export(IOFormat iOFormat) {
		return underlayingMarc.export(iOFormat);
	}

	
	protected boolean isBook(){		
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		String ldr07 = Character.toString(underlayingMarc.getLeader().getImplDefined1()[0]);
		
		String f006 = underlayingMarc.getControlField("006");
	    String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
	    String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
		if(ldr06.matches("(?i)[at]") && ldr07.matches("(?i)[cdm]"))	return true;		
		if(f007_00.matches("(?i)t")) return true;		
		if(f006_00.matches("(?i)[at]")) return true;
		
		return false;		
	}
	
	protected boolean isPeriodical(){
		String ldr07 = Character.toString(underlayingMarc.getLeader().getImplDefined1()[0]);
		
		if(ldr07.matches("(?i)[is]")) return true;		
		
		return false;		
	}
	
	protected boolean isArticle(){
		String ldr07 = Character.toString(underlayingMarc.getLeader().getImplDefined1()[0]);
		
		if(ldr07.matches("(?i)[ab]")) return true;		
		
		return false;		
	}
	
	protected boolean isMap(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
		String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
	    String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
		String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
		
		if(ldr06.matches("(?i)[ef]")) return true;
		if(f006_00.matches("(?i)[ef]")) return true;
		if(f245h.matches("(?i)kartografický\\sdokument")) return true;
		if(f007_00.matches("(?i)a")) return true;
		if(f336b.matches("(?i)cr.*")) return true;
		
		return false;		
	}
	
	protected boolean isMusicalScores(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
		String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
		String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";

		if(ldr06.matches("(?i)[cd]"))return true;
		if(f006_00.matches("(?i)[cd]") && f245h.equalsIgnoreCase("hudebnina")){
			return true;
		}
		if(f336b.equalsIgnoreCase("tcm")) return true;
		if(f336b.equalsIgnoreCase("ntm")) return true;
		if(f336b.equalsIgnoreCase("ntv")) return true;
		if(f336b.equalsIgnoreCase("tcn")) return true;
		
		return false;		
	}
	
	protected boolean isVisualDocument(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
		String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
		String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
		String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";

		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
		
		String f337b = underlayingMarc.getField("337", 'b');
		if(f337b == null) f337b = "";
		
		String f338b = underlayingMarc.getField("338", 'b');
		if(f338b == null) f338b = "";
		
		if(ldr06.matches("(?i)[kg]")) return true;		
		if(f007_00.matches("(?i)[kg]")) return true;
		if(f245h.equalsIgnoreCase("grafika")) return true;
		if(f006_00.matches("(?i)[kg]")) return true;
		if(f336b.matches("(?i)sti|tci|cri|crt")) return true;
		if(f337b.matches("(?i)g")) return true;
		if(f338b.matches("(?i)g.*")) return true;		
		
		return false;		
	}
	
	protected boolean isManuscript(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
		String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
		String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
				
		if(ldr06.matches("(?i)[tdf]")) return true;
		if(f006_00.matches("(?i)[tdf]")) return true;
		if(f245h.equalsIgnoreCase("rukopis")) return true;
		
		return false;
	}
	
	protected boolean isMicroform(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
				
		String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
		String f008 = underlayingMarc.getControlField("008");
	    String f008_23 = (f008 != null) && (f008.length() > 23) ? Character.toString(f008.charAt(23)) : "";
	    String f008_29 = (f008 != null) && (f008.length() > 29) ? Character.toString(f008.charAt(29)) : "";

		String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		String f337b = underlayingMarc.getField("337", 'b');
		if(f337b == null) f337b = "";
		
		String f338b = underlayingMarc.getField("338", 'b');
		if(f338b == null) f338b = "";
		
		if(ldr06.matches("(?i)[acdpt]") && f008_23.matches("(?i)[abc]")) return true;
		if(ldr06.matches("(?i)[efk]") && f008_29.matches("(?i)b")) return true;
		if(f007_00.matches("(?i)h")) return true;
		if(f245h.equalsIgnoreCase("MIKRODOKUMENT")) return true;
		if(f337b.matches("(?i)h")) return true;
		if(f338b.matches("(?i)h.*")) return true;
		
		return false;
	}
	
	protected boolean isLargePrint () {
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
	    String f006 = underlayingMarc.getControlField("006");
		String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		String f006_06 = (f006 != null) && (f006.length() > 6) ? Character.toString(f006.charAt(6)) : "";
	    String f006_12 = (f006 != null) && (f006.length() > 12) ? Character.toString(f006.charAt(12)) : "";
	    
	    String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		String f007_01 = (f007 != null) && (f007.length() > 1) ? Character.toString(f007.charAt(1)) : "";
	    
		String f008 = underlayingMarc.getControlField("008");
	    String f008_23 = (f008 != null) && (f008.length() > 23) ? Character.toString(f008.charAt(23)) : "";
	    String f008_29 = (f008 != null) && (f008.length() > 29) ? Character.toString(f008.charAt(29)) : "";

		if(ldr06.matches("(?i)[acdpt]") && f008_23.matches("(?i)d")){
			return true;
		}
		if(ldr06.matches("(?i)[efk]") && f008_29.matches("(?i)d")){
			return true;
		}
		if(f006_00.matches("(?i)[acdpt]") && f006_06.matches("(?i)d")){
			return true;
		}
		if(f006_00.matches("(?i)[efk]") && f006_12.matches("(?i)[d)")){
			return true;
		}
		if(f007_00.matches("(?i)d") && f007_01.matches("(?i)b")){
			return true;
		}
		if(f007_00.matches("(?i)t") && f007_01.matches("(?i)b")){
			return true;
		}
		return false;
	}
	
	protected boolean isBraill(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
	    String f006 = underlayingMarc.getControlField("006");
		String f006_06 = (f006 != null) && (f006.length() > 6) ? Character.toString(f006.charAt(6)) : "";
	    String f006_12 = (f006 != null) && (f006.length() > 12) ? Character.toString(f006.charAt(12)) : "";
	    
	    String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		String f007_01 = (f007 != null) && (f007.length() > 1) ? Character.toString(f007.charAt(1)) : "";
	    
		String f008 = underlayingMarc.getControlField("008");
	    String f008_23 = (f008 != null) && (f008.length() > 23) ? Character.toString(f008.charAt(23)) : "";
	    String f008_29 = (f008 != null) && (f008.length() > 29) ? Character.toString(f008.charAt(29)) : "";

	    String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
	    
		if(ldr06.matches("(?i)[acdpt]") && f008_23.matches("(?i)f")){
			return true;
		}
		if(ldr06.matches("(?i)[acdpt]") && f006_06.matches("(?i)f")){
			return true;
		}
		if(f007_00.matches("(?i)f") && f007_01.matches("(?i)b") && f245h.matches("(?i)hmatové\\spísmo")){
			return true;
		}
		if(ldr06.matches("(?i)[efk]")&& f008_29.matches("(?i)f")){
			return true;
		}
		if(ldr06.matches("(?i)[efk]") && f006_12.matches("(?i)f")){
			return true;
		}
		
		if(f007_00.matches("(?i)t") && f007_01.matches("(?i)c")){
			return true;
		}
		if(f336b.matches("(?i)tct|tcm|tci|tcf")) return true;
		
		return false;
	}
	
	protected boolean isElectronicSource(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
	    String f006 = underlayingMarc.getControlField("006");
	    String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		String f006_06 = (f006 != null) && (f006.length() > 6) ? Character.toString(f006.charAt(6)) : "";
		
	    String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
		String f008 = underlayingMarc.getControlField("008");
	    String f008_23 = (f008 != null) && (f008.length() > 23) ? Character.toString(f008.charAt(23)) : "";
	    String f008_29 = (f008 != null) && (f008.length() > 29) ? Character.toString(f008.charAt(29)) : "";

	    String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
		
		String f338b = underlayingMarc.getField("338", 'b');
		if(f338b == null) f338b = "";
		
		if(f245h.matches("(?i)elektronický\\szdroj")){
			return true;
		}
		if(ldr06.matches("(?i)[acdijpt]") && f008_23.matches("(?i)[soq]")){
			return true;
		}
		if(f006_00.matches("(?i)[acdijpt]") && f006_06.matches("(?i)[soq]")){
			return true;
		}
		if(ldr06.matches("(?i)[efgkopr]") && f008_29.matches("(?i)[soq]")){
			return true;
		}
		
		if(f006_00.matches("(?i)[efgkopr]") && f006_06.matches("(?i)[soq]")){
			return true;
		}
		if(ldr06.matches("(?i)m") && f006_00.matches("(?i)m")){
			return true;
		}
		if(f007_00.matches("(?i)c")) return true;
		if(f336b.matches("(?i)cod|cop")) return true;
		if(f338b.matches("(?i)cr|ck|cb|cd|ce|ca|cf|ch|cz")) return true;
		
		
		return false;
	}
	
	protected HarvestedRecordFormat getAudioFormat(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
	    String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
	    String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
	    String f007_01 = (f007 != null) && (f007.length() > 1) ? Character.toString(f007.charAt(1)) : "";
	    	    
		String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		List<DataField> f300 = underlayingMarc.getDataFields("300");
		List<DataField> f500 = underlayingMarc.getDataFields("500");
		
		String f300a = underlayingMarc.getField("300", 'a');
		if(f300a == null) f300a = "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
		
		String f337b = underlayingMarc.getField("337", 'b');
		if(f337b == null) f337b = "";
				
		String f338b = underlayingMarc.getField("338", 'b');
		if(f338b == null) f338b = "";
				
		// AUDIO_CD
		// zvukov(aáeé|ych|ých)\sdes(ka|ky|ek) AND (digital  or 12 cm)
		boolean b1 = false, b2 = false;
		for(DataField df: f300){
			for (Subfield sf: df.getSubfields()){
				if(sf.getData().matches("(?i)kompaktn[ií]\\sdisk")) return HarvestedRecordFormat.AUDIO_CD;
				if(sf.getData().matches("(?i)zvukov[eéaá]\\sCD")) return HarvestedRecordFormat.AUDIO_CD;
				if(sf.getData().matches("(?i)zvukov([aáeé]|ych|ých)\\sdes(ka|ky|ek)")) b1 = true;
				if(sf.getData().matches("(?i)(digital|12\\scm)")) b2 = true;
			}
		}
		if(b1 && b2) return HarvestedRecordFormat.AUDIO_CD;
		
		for(DataField df: f500){
			for (Subfield sf: df.getSubfields()){
				if(sf.getData().matches("(?i)kompaktn[ií]\\sdisk")) return HarvestedRecordFormat.AUDIO_CD;
			}
		}
		
		// AUDIO_DVD
		if(ldr06.matches("(?i)[ij]") && f300a.matches("(?i)dvd")) return HarvestedRecordFormat.AUDIO_DVD;
		
		// AUDIO_LP
		// zvukov(aáeé|ych|ých)\sdes(ka|ky|ek) AND (analog)
		b1 = false; b2 = false;
		for(DataField df: f300){
			for (Subfield sf: df.getSubfields()){
				if(sf.getData().matches("(?i)gramofonov([aáeé]|ych|ých)\\sdes(ka|ky|ek)")) return HarvestedRecordFormat.AUDIO_LP;				
				if(sf.getData().matches("(?i)zvukov([aáeé]|ych|ých)\\sdes(ka|ky|ek)")) b1 = true;
				if(sf.getData().matches("(?i)analog")) b2 = true;
			}
		}
		if(b1 && b2) return HarvestedRecordFormat.AUDIO_LP;
		
		// AUDIO_CASSETTE
		if(ldr06.matches("(?i)[ij]") && f007_00.matches("(?i)s")) return HarvestedRecordFormat.AUDIO_CASSETTE;
		if(f007_00.matches("(?i)s") && f338b.matches("(?i)ss")) return HarvestedRecordFormat.AUDIO_CASSETTE;
		if(f007_00.matches("(?i)s") && f007_01.matches("(?i)[zgeiqt]")) return HarvestedRecordFormat.AUDIO_CASSETTE;
			
		for(DataField df: f300){
			for (Subfield sf: df.getSubfields()){
				if(sf.getData().matches("(?i)zvukov(a|á|e|é|ych|ých)\\skaze(ta|ty|t)")) return HarvestedRecordFormat.AUDIO_CASSETTE;
				if(sf.getData().matches("(?i).*(mc|kz|mgk).*")) return HarvestedRecordFormat.AUDIO_CASSETTE;
			}
		}
		
		if(ldr06.matches("(?i)[ij]")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f007_00.matches("(?i)s")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f245h.matches("(?i)zvukový\\száznam")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f337b.matches("(?i)s")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f006_00.matches("(?i)[ij]")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f338b.matches("(?i)s.*")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f007_00.matches("(?i)i")) return HarvestedRecordFormat.AUDIO_OTHER;
		if(f336b.matches("(?i)spw|snd")) return HarvestedRecordFormat.AUDIO_OTHER;
		
		return null;
	}
	
	protected HarvestedRecordFormat getVideoDocument(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
		String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
	    String f006_16 = (f006 != null) && (f006.length() > 16) ? Character.toString(f006.charAt(16)) : "";
		
		String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
	    String f007_04 = (f007 != null) && (f007.length() > 4) ? Character.toString(f007.charAt(4)) : "";
		
	    String f008 = underlayingMarc.getControlField("008");
	    String f008_33 = (f008 != null) && (f008.length() > 33) ? Character.toString(f008.charAt(33)) : "";
	    
	    String f245h = underlayingMarc.getField("245", 'h');		
		if(f245h == null) f245h = "";
		
		List<DataField> f300 = underlayingMarc.getDataFields("300");
		
		String f300a = underlayingMarc.getField("300", 'a');
		if(f300a == null) f300a = "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
		
		String f337b = underlayingMarc.getField("337", 'b');
		if(f337b == null) f337b = "";
		
		String f338b = underlayingMarc.getField("338", 'b');
		if(f338b == null) f338b = "";
		
		// Bluray
		if(ldr06.matches("(?i)g")){
			for(DataField df: f300){
				for (Subfield sf: df.getSubfields()){
					if(sf.getData().matches("(?i)blu.*ray")) return HarvestedRecordFormat.VIDEO_BLURAY;
				}
			}
		}
		
		// VHS
		for(DataField df: f300){
			for (Subfield sf: df.getSubfields()){
				if(sf.getData().matches("(?i)vhs")) return HarvestedRecordFormat.VIDEO_VHS;
			}
		}
		if(f007_00.matches("(?i)v") && f007_04.matches("(?i)b")) return HarvestedRecordFormat.VIDEO_VHS;
		
		// DVD
		if(ldr06.matches("(?i)g") && f300a.matches("(?i)dvd")) return HarvestedRecordFormat.VIDEO_DVD;	
		if(f007_00.matches("(?i)v") && f007_04.matches("(?i)v")) return HarvestedRecordFormat.VIDEO_DVD;
		
		// others
		if(ldr06.matches("(?i)g")) return HarvestedRecordFormat.VIDEO_OTHER;
		
		if(f245h.matches("(?i)videozáznam")) return HarvestedRecordFormat.VIDEO_OTHER;
		if(f337b.matches("(?i)v")) return HarvestedRecordFormat.VIDEO_OTHER;
		if(ldr06.matches("(?i)g") && f008_33.matches("(?i)[mv]")) return HarvestedRecordFormat.VIDEO_OTHER;
		if(f006_00.matches("(?i)g") && f006_16.matches("(?i)[mv]")) return HarvestedRecordFormat.VIDEO_OTHER;
		if(f338b.matches("(?i)v.*")) return HarvestedRecordFormat.VIDEO_OTHER;
		if(f336b.matches("(?i)tdi|tdm")) return HarvestedRecordFormat.VIDEO_OTHER;
		
		if(f338b.matches("(?i)vr|vz|vc|mc|mf|mr|mo|mz")) return HarvestedRecordFormat.VIDEO_OTHER;
		
		return null;
	}
	
	protected boolean isKit(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
	    String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
		String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
	    if(ldr06.matches("(?i)o")) return true;
	    if(f006_00.matches("(?i)o") && f007_00.matches("(?i)o")) return true;
		return false;
	}
	
	protected boolean isObject(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
		String f006 = underlayingMarc.getControlField("006");
	    String f006_00 = (f006 != null) && (f006.length() > 0) ? Character.toString(f006.charAt(0)) : "";
		
		String f008 = underlayingMarc.getControlField("008");
	    String f008_33 = (f008 != null) && (f008.length() > 33) ? Character.toString(f008.charAt(33)) : "";
	    		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
				
	    if(ldr06.matches("(?i)r")) return true;
	    if(f336b.matches("(?i)tcf|tdm|tdf")) return true;
	    if(f008_33.matches("(?i)d")) return true;
	    if(f006_00.matches("(?i)r")) return true;
		return false;
	}
	
	protected boolean isMixDocument(){
		String ldr06 = Character.toString(underlayingMarc.getLeader().getTypeOfRecord());
		
	    if(ldr06.matches("(?i)p")) return true;
		return false;
	}
	
	protected boolean isUnspecified(){
		String f007 = underlayingMarc.getControlField("007");
	    String f007_00 = (f007 != null) && (f007.length() > 0) ? Character.toString(f007.charAt(0)) : "";
		
		String f336b = underlayingMarc.getField("336", 'b');
		if(f336b == null) f336b = "";
		
		String f337b = underlayingMarc.getField("337", 'b');
		if(f337b == null) f337b = "";
				
		String f338b = underlayingMarc.getField("338", 'b');
		if(f338b == null) f338b = "";
		
		if(f007_00.matches("(?i)z") && f336b.matches("(?i)zzz")) return true;
		if(f337b.matches("(?i)[xz]")) return true;
		if(f338b.matches("(?i)zu")) return true;
		
		return false;
	}
	
	@Override
	public List<HarvestedRecordFormat> getDetectedFormatList() {
		List<HarvestedRecordFormat> hrf = new ArrayList<HarvestedRecordFormat>();
		
		if(isBook()) hrf.add(HarvestedRecordFormat.BOOKS);
		if(isPeriodical()) hrf.add(HarvestedRecordFormat.PERIODICALS);
		if(isArticle()) hrf.add(HarvestedRecordFormat.ARTICLES);
		if(isMap()) hrf.add(HarvestedRecordFormat.MAPS);
		if(isMusicalScores()) hrf.add(HarvestedRecordFormat.MUSICAL_SCORES);
		if(isVisualDocument()) hrf.add(HarvestedRecordFormat.VISUAL_DOCUMENTS);
		if(isManuscript()) hrf.add(HarvestedRecordFormat.MANUSCRIPTS);
		if(isMicroform()) hrf.add(HarvestedRecordFormat.MICROFORMS);
		if(isLargePrint()) hrf.add(HarvestedRecordFormat.LARGE_PRINTS);
		if(isBraill()) hrf.add(HarvestedRecordFormat.BRAILL);
		if(isElectronicSource()) hrf.add(HarvestedRecordFormat.ELECTRONIC_SOURCE);
		HarvestedRecordFormat audio = getAudioFormat();
		if(audio != null) hrf.add(audio);
		HarvestedRecordFormat video = getVideoDocument();
		if(video != null) hrf.add(video);
		if(isKit()) hrf.add(HarvestedRecordFormat.KIT);
		if(isObject()) hrf.add(HarvestedRecordFormat.OBJECT);
		if(isMixDocument()) hrf.add(HarvestedRecordFormat.MIX_DOCUMENT);
		if(isUnspecified()) hrf.add(HarvestedRecordFormat.UNSPECIFIED);		
		if(hrf.isEmpty()) hrf.add(HarvestedRecordFormat.UNSPECIFIED);
		
		return hrf;
	}
		
}