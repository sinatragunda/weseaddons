package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.impl.FxDAODelete;

public class FxReverseDeal {


	public static void reverse(long id ,FxDAODelete fxDAODelete){

		OffshoreThread.run(fxDAODelete.delete(id));
	}
}