package com.quantiguous.iib.tutorial;

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
		elementList.get(0).setValue("haha");
		elementList.get(1).setValue("bye");
		

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);

		// Change following to propagate the message to the 'out' or 'alt' terminal
		out.propagate(outAssembly);
	}

}
