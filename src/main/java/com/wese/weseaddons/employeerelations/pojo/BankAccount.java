/*Created by Sinatra Gunda
  At 16:54 on 9/30/2020 */

package com.wese.weseaddons.employeerelations.pojo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.pojo.Client;

public class BankAccount implements PojoInterface {

	private Long id ;
	private String bankName ;
	private String accountNumber ;
	private String branch ;
	private String accountName ;
	private boolean active ;
	private Client client ;


	public BankAccount(){}


	public BankAccount(Long id){
		this.id = id ;
	}

	public ObjectNode objectNode(){
		ObjectNode objectNode = Helper.statusNodes(true);

		objectNode.put("bankName" ,bankName);
		objectNode.put("accountNumber" ,accountNumber);
		objectNode.put("accountName" ,accountName);
		objectNode.put("id" ,id);
		objectNode.put("active",active);
		objectNode.put("branch" ,branch);
		objectNode.put("clientId" ,client.getId());

		return objectNode;

	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String getSchema(){
		return "m_app_client_bank_account";
	}
}