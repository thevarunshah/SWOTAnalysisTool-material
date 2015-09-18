package com.thevarunshah.swotanalysistool.backend;

import java.util.HashMap;

public class Database {

	private static int id = 100;
	private static HashMap<Integer, SWOTObject> SWOTs = new HashMap<Integer, SWOTObject>();
	private static SWOTObject currentSWOT = null;

	public static int getId(){
		return id;
	}

	public static HashMap<Integer, SWOTObject> getSWOTs() {
		return SWOTs;
	}

	public static SWOTObject getCurrentSWOT() {
		return currentSWOT;
	}

	public static void setCurrentSWOT(SWOTObject currentSWOT) {
		Database.currentSWOT = currentSWOT;
	}

	public static void setId(int id) {
		Database.id = id;
	}

	public static void setSWOTs(HashMap<Integer, SWOTObject> sWOTs) {
		SWOTs = sWOTs;
	}

}
