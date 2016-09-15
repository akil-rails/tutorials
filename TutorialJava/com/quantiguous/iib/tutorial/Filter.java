package com.quantiguous.iib.tutorial;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;

public class Filter extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
		
		Connection conn = getJDBCType4Connection("REMITTANCE", JDBC_TransactionType.MB_TRANSACTION_AUTO);
		String columnNames = "";
		
	/*	
        try {
				Class.forName("oracle.jdbc.OracleDriver");
				
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.211.55.4:1521:XE" ,"inw","inw");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
		

		
		/*
		MbMessage outMessage = new MbMessage(inAssembly.getMessage());
		outMessage.getRootElement().getLastChild().getLastChild().setValue("from Java");
		MbElement abc = outMessage.getRootElement().getLastChild().getLastChild();
		
		abc.createElementAsLastChild(MbElement.TYPE_NAME, "myChild", null);
		abc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "company", "quantiguous");
		*/

		/*
		MbMessage outMessage = new MbMessage();
		MbElement xml = outMessage.getRootElement().createElementAsFirstChild(MbXMLNSC.PARSER_NAME);
		xml.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "company", "quantiguous");
		*/

		/*
		MbMessage outMessage = new MbMessage();
		MbElement json = outMessage.getRootElement().createElementAsFirstChild(MbJSON.ROOT_ELEMENT_NAME);
		MbElement dataElement = json.createElementAsLastChild(MbElement.TYPE_NAME, "Data", null);
		dataElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "company", "quantiguous");
		*/

		/*
		MbMessage outMessage = new MbMessage();
		MbElement replyHeader = outMessage.getRootElement().createElementAsLastChild(MbElement.TYPE_NAME, "HTTPReplyHeader", null);
		replyHeader.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Content-Type", "text/text");
		
		MbElement blob = replyHeader.createElementAfter(MbBLOB.ROOT_ELEMENT_NAME);
		MbElement dataElement = blob.createElementAsLastChild(MbElement.TYPE_NAME, "BLOB", "something".getBytes());
		*/

		MbMessage outMessage = new MbMessage();
		MbElement xml = outMessage.getRootElement().createElementAsFirstChild(MbXMLNSC.PARSER_NAME);
		List<MbElement> elementList = (List<MbElement>) xml.evaluateXPath("abc");
		int numberOfElement = elementList.size();
		elementList = (List<MbElement>) xml.evaluateXPath("?abc/?xyz");
		numberOfElement = elementList.size();
		elementList = (List<MbElement>) xml.evaluateXPath("?abc/?def");
		numberOfElement = elementList.size();
		elementList = (List<MbElement>) xml.evaluateXPath("?abc/*");
		numberOfElement = elementList.size();
		elementList.get(0).setValue(columnNames);
		elementList.get(1).setValue("bye");
		
		
		try {
			int value = 10051;
			
			CallableStatement call_stmt = conn.prepareCall("begin ? := my_function(?, ?, ?); end;");
			
			int i = 1;
			call_stmt.registerOutParameter(i++, java.sql.Types.INTEGER);
			call_stmt.setInt(i++, value);
			call_stmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
			call_stmt.registerOutParameter(i++, -10);
			
			call_stmt.execute();
			
			int retVal = call_stmt.getInt(1);
			String retString = call_stmt.getString(3);
			ResultSet rs = (ResultSet) call_stmt.getObject(4);
			
			ResultSetMetaData rd = rs.getMetaData();
			

	
			while (rs.next()) {
				
				for (int j=1; j <= rd.getColumnCount(); j++) {
					elementList = (List<MbElement>) xml.evaluateXPath("?abc/?$row/?" + rd.getColumnName(j));
					numberOfElement = elementList.size();
					try {
					elementList.get(0).setValue(rs.getString(j));
					}
					 catch (Exception e) {
						 ;
					 }

				}
				
				int id = rs.getInt("ID");
				Timestamp ts = rs.getTimestamp("REQ_TIMESTAMP");
				String reqNo = rs.getString("REQ_NO");
			}
			
			PreparedStatement prep_stmt = conn.prepareStatement("select id,req_no from inward_remittances where id = ?");
			prep_stmt.setInt(1, value);
			prep_stmt.execute();
			
			rs = prep_stmt.getResultSet();
			
			while (rs.next()) {
				String reqNo = rs.getString("REQ_NO");
				int id = rs.getInt("ID");
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);

		// Change following to propagate the message to the 'out' or 'alt' terminal
		out.propagate(outAssembly);
	}

}
