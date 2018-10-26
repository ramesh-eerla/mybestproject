/*
 * Copyright (C) 2013 km innozol IT solutions Pvt Ltd <http://innozol.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.innozol.stallion.wishes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;



/**
 * A Serializable class which sort list of dates according to Calendar object.
 * To sort a list of items use sort() method.
 * @author Ashwin
 */
public class SortingWishList implements Serializable{
	
	/**
	 * serialVersionUID = 14L
	 */
	private static final long serialVersionUID = 14L;


	public ArrayList<BirthDayDooot> dooots;
	
	
	/**
	 * An AutomicBoolean value used to check the list sorted. 
	 */
	private AtomicBoolean isSorted = new AtomicBoolean(false);
	
	/**
	 * Pass Unsorted list
	 * @param unSorted List of unsorted BirthDayDoot
	 */
	public void setList(ArrayList<BirthDayDooot> unSorted){
		this.dooots = unSorted;
		this.isSorted.set(false);
	}
	
	/**
	 * Sort the List object in Calendar wise.
	 * @param unSortDooots An unsorted List object.
	 */
	public void sort(){
		ArrayList<BirthDayDooot> temp = calendarCalculations();
		Collections.sort(temp, new Comparator<BirthDayDooot>() {
			@Override
			public int compare(BirthDayDooot lhs, BirthDayDooot rhs) {
				lhs.getDootBirthDay().clear(Calendar.MILLISECOND);
				rhs.getDootBirthDay().clear(Calendar.MILLISECOND);
				return (lhs.getDootBirthDay().before(rhs.getDootBirthDay()) ? -1 : 
					(lhs.getDootBirthDay().compareTo(rhs.getDootBirthDay())== 0 ? 0 : 1));
			}
		});
		isSorted.set(true);
		setSortedList(temp);
	}
	
	private ArrayList<BirthDayDooot> calendarCalculations(){
		Calendar calendar = Calendar.getInstance();
		ArrayList<BirthDayDooot> temp = new ArrayList<BirthDayDooot>();
		for(BirthDayDooot dooot : dooots){
			Calendar bCal = dooot.getDootBirthDay();
			if(CalendarUtils.isInsaneDate(bCal)){
				bCal.set(Calendar.YEAR, CalendarUtils.getNextLeap(CalendarUtils.getCurrentYear()));
				dooot.setDootBirthday(bCal);
				dooot.setBDay(CalendarUtils.styleDate(bCal));
			}
			else{
				bCal.set(Calendar.YEAR, CalendarUtils.getCurrentYear());
				dooot.setDootBirthday(bCal);
				dooot.setBDay(CalendarUtils.styleDate(bCal));
			}
			if(CalendarUtils.isToday(bCal)){
				bCal.set(Calendar.YEAR, CalendarUtils.getCurrentYear());
				dooot.setDootBirthday(bCal);
				dooot.setBDay("Today");
			}
			else if(bCal.before(calendar)){
				bCal.set(Calendar.YEAR, CalendarUtils.getCurrentYear() + 1);
				dooot.setDootBirthday(bCal);
				dooot.setBDay(CalendarUtils.styleDate(bCal));
			}
			else if(CalendarUtils.isComingWeek(bCal)){
				dooot.setBDay(CalendarUtils.getWeekDay(bCal));
			} 
			if(CalendarUtils.isTomorrow(bCal)){
				dooot.setBDay("Tomorrow");
			}
			temp.add(dooot);
		}
		return temp;
	}
	
	
	public void sortByName(){
		ArrayList<BirthDayDooot> temp = calendarCalculations();
		Collections.sort(temp, new Comparator<BirthDayDooot>() {
			@Override
			public int compare(BirthDayDooot lhs, BirthDayDooot rhs) {
				return (lhs.getDootBirthDay().before(rhs.getDootBirthDay()) ? -1 : 
					(lhs.getDootBirthDay().compareTo(rhs.getDootBirthDay())== 0 ? 0 : 1));
			}
		});
	}
	
	/**
	 * Get the list of today Event list.
	 * @return today event list of BirthDayDooot. Returns empty list if not sorted 
	 */
	public ArrayList<BirthDayDooot> todayList(){
		ArrayList<BirthDayDooot> todayDooots = new ArrayList<BirthDayDooot>();
		if(isSorted.get()){
			for(BirthDayDooot dooot:dooots){
				if(CalendarUtils.isToday(dooot.getDootBirthDay())){
					todayDooots.add(dooot);
				}
			}
		}
		else{
			sort();
		}
		return todayDooots;
	}
	
	private void setSortedList(ArrayList<BirthDayDooot> doots){
		this.dooots = doots;
	}
	
	
	/**
	 * Get the list of tomorrow Event list.
	 * @return tomorrow event list of BirthDayDooot. Returns empty list if not sorted 
	 */
	public ArrayList<BirthDayDooot> tomorrowList(){
		ArrayList<BirthDayDooot> todayDooots = new ArrayList<BirthDayDooot>();
		if(isSorted.get()){
			for(BirthDayDooot dooot:dooots){
				if(CalendarUtils.isTomorrow(dooot.getDootBirthDay())){
					todayDooots.add(dooot);
				}
			}
		}
		else{
			sort();
		}
		return todayDooots;
	}
	
	/**
	 * List after sorting Calendar wise.
	 * @return Sorted list
	 */
	public ArrayList<BirthDayDooot> getSortedList(){
		if(isSorted.get()){
			return dooots;
		}else{
			sort();
			return dooots;
		}
	}
	
	/**
	 * @return true if list is already sorted.
	 */
	public boolean isListSorted(){
		return isSorted.get();
	}
	
}
