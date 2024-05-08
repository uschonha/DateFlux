package de.uschonha.dateflux;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Converter from Gregorian Date to Discordian.
 * 
 * @author uschonha
 */
public class DiscordianDate {

	// private Date date = new Date();
	private Calendar cal = Calendar.getInstance();

	private static final int DISCORDIAN_SEASON_LENGTH = 73;
	private static final int DISCORDIAN_WEEK_LENGTH = 5;

	private int discordianDayOfSeason = -1;
	private int discordianWeekday = -1;
	private int discordianSeason = -1;
	private int discordianWeek = -1;
	private int discordianYear = -1;

	private int dayOfYear = -1;
	private int year = -1;
	private boolean leapYear = false;
	private boolean leapYearSpecialDay = false;

	// -><- St. Tib's day is considered outside of the Discordian week
	public static final String DISCORDIAN_LEAP_YEAR_DAY = "St. Tib's Day";

	public enum DiscordianApostleHolyday5 {
		Mungday, Mojoday, Syaday, Zaraday, Maladay;
	}

	public enum DiscordianSeasonHolyday50 {
		Chaoflux, Discoflux, Confuflux, Bureflux, Afflux;
	}

	public enum DiscordianWeekday { 
		Sweetmorn("Sweetmorn"), Boomtime("Boomtime"), Pungenday("Pungenday"), 
		Prickle_Prickle("Prickle-Prickle"), Setting_Orange("Setting Orange");
		private String name;
		DiscordianWeekday(String name) {
			this.name = name;
		}
		public String toString() {
			return name;
		}
	}

	public enum DiscordianSeason {
		Chaos("Chaos"), Discord("Discord"), Confusion("Confusion"), 
		Bureaucracy("Bureaucracy"), The_Aftermath("The Aftermath");
		private String name;
		DiscordianSeason(String name) {
			this.name = name;
		}
		public String toString() {
			return name;
		}
	}

	public enum DiscordianSeasonShort {
		Chs("Chs"), Dsc("Dsc"), Cfn("Cfn"), Bcy("Bcy"), Afm("Afm");
		private String name;
		DiscordianSeasonShort(String name) {
			this.name = name;
		}
		public String toString() {
			return name;
		}
	}

	public DiscordianDate() {
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		transmogrify();
	}

	public DiscordianDate(Date aDate) {
		cal.setTime(aDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		transmogrify();
	}

	private boolean isLeapYear(int year) {
		boolean retBoo = false;
		if (year % 400 == 0) {
			retBoo = true;
		} else if (year % 100 == 0) {
			retBoo = false;
		} else if (year % 4 == 0) {
			retBoo = true;
		} else {
			retBoo = false;
		}
		return retBoo;
	}

	/**
	 * Take current Gregorian Date and do the math for Discordian.
	 */
	private void transmogrify() {
		// System.out.println("Date = " + date);
		dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		// System.out.println("DAY_OF_YEAR = " + dayOfYear);
		year = cal.get(Calendar.YEAR);
		// -><- the Curse of Greyface occurred in 1166 B.C.
		discordianYear = year + 1166; 
		// System.out.println("Year = " + year + ", Discordian Year = " + discordianYear);

		// -><- do the normal year
		discordianWeekday = (dayOfYear - 1) % DISCORDIAN_WEEK_LENGTH;
		discordianWeek = ((dayOfYear - 1) / DISCORDIAN_WEEK_LENGTH) + 1;
		discordianSeason = (dayOfYear - 1) / DISCORDIAN_SEASON_LENGTH;
		discordianDayOfSeason = (dayOfYear - 1) % DISCORDIAN_SEASON_LENGTH + 1;

		leapYear = isLeapYear(year);
		// System.out.println("isLeapYear = " + leapYear);
		if (leapYear) {
			int month = cal.get(Calendar.MONTH);
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (month == Calendar.FEBRUARY && dayOfMonth == 29) {
				// -><- Leap Year special Day
				leapYearSpecialDay = true;
				discordianWeekday = 0;
				discordianDayOfSeason = 0;
			} else {
				leapYearSpecialDay = false;
			}
			// -><- adjust after special Day and recalculate
			if (month >= Calendar.MARCH) {
				dayOfYear--;
				discordianWeekday = (dayOfYear - 1) % DISCORDIAN_WEEK_LENGTH;
				discordianWeek = ((dayOfYear - 1) / DISCORDIAN_WEEK_LENGTH) + 1;
				discordianSeason = (dayOfYear - 1) / DISCORDIAN_SEASON_LENGTH;
				discordianDayOfSeason = (dayOfYear - 1) % DISCORDIAN_SEASON_LENGTH + 1;
			}
		} 
		// System.out.println("discordianWeek = " + discordianWeek);
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public int getDayOfMonth() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public int getDiscordianYear() {
		return discordianYear;
	}

	public int getDiscordianWeek() {
		return discordianWeek;
	}

	public int getDiscordianDayOfSeason() {
		return discordianDayOfSeason;
	}

	public String getDiscordianSeason() {
		return DiscordianSeason.values()[discordianSeason].toString();
	}

	public String getDiscordianSeasonShort() {
		return DiscordianSeasonShort.values()[discordianSeason].toString();
	}

	public String getDiscordianWeekday() {
		if (leapYearSpecialDay) {
			return DISCORDIAN_LEAP_YEAR_DAY;
		}
		return DiscordianWeekday.values()[discordianWeekday].toString();
	}
	
	public int getDiscordianWeekdayNr() {
		if (leapYearSpecialDay) {
			return -1; // TODO ??? what happens on St.TibsDay ???
		} else {
			return discordianWeekday;
		}
	}

	public String getDiscordianHolyday() {
		String retStr = null;
		if (discordianDayOfSeason == 5) {
			retStr = DiscordianApostleHolyday5.values()[discordianSeason].toString();
		} else if (discordianDayOfSeason == 50) {
			retStr = DiscordianSeasonHolyday50.values()[discordianSeason].toString();
		} else if (leapYearSpecialDay) {
			retStr = DISCORDIAN_LEAP_YEAR_DAY;
		}
		return retStr;
	}

	public String toString() {
		String retStr = "";
		// retStr += "(DayOfYear = " + dayOfYear + ") - ";
		retStr += getDiscordianWeekday() + ", ";
		retStr += getDiscordianSeason() + " " + discordianDayOfSeason;
		retStr += " in the YOLD " + discordianYear;
		// retStr += " (weekNr = " + discordianWeek + ")";
		String holyday = getDiscordianHolyday();
		if (holyday != null) {
			retStr += " - Celebrate " + holyday;
		}
		return retStr;
	}

	public void rollDayOfYear(int amount) {
		cal.add(Calendar.DAY_OF_YEAR, amount);
		transmogrify();
	}
	
	public void rollYear(int amount) {
		cal.add(Calendar.YEAR, amount);
		transmogrify();
	}
	
	public Date getGregorianDate() {
		return cal.getTime();
	}
	
	public String getGregorianDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEE", Locale.US);
		return sdf.format(cal.getTime());
	}

	public static String getGregorianDateKeyStr(Date aDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEE", Locale.US);
		return sdf.format(aDate);		
	}
}
