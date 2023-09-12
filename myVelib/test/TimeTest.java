package test;

import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.Ignore;

import top.Time;

class TimeTest {

	@Test
	void testCalTimeDifference() {
		System.out.println(Time.calTimeDifference(new int[]{23,19,17}, new int[]{23,22,11}));
		System.out.println(Time.calTimeDifference(new int[]{23,19,17}, new int[]{23,19,17}));
		System.out.println(Time.calTimeDifference(new int[]{23,19,17}, new int[]{23,18,16}));
	}

}
