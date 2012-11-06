package org.mule.module.gmail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.module.gmail.search.FlagCriteria;
import org.mule.module.gmail.search.SearchCriteria;

import com.google.code.com.sun.mail.imap.IMAPFolder;
import com.google.code.com.sun.mail.imap.IMAPMessage;
import com.google.code.javax.mail.FetchProfile;
import com.google.code.javax.mail.Folder;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.Store;
import com.google.code.javax.mail.internet.InternetAddress;
import com.google.code.javax.mail.search.ComparisonTerm;
import com.google.code.javax.mail.search.FlagTerm;
import com.google.code.javax.mail.search.FromTerm;
import com.google.code.javax.mail.search.GmailLabelTerm;
import com.google.code.javax.mail.search.GmailRawSearchTerm;
import com.google.code.javax.mail.search.MessageNumberTerm;
import com.google.code.javax.mail.search.OrTerm;
import com.google.code.javax.mail.search.ReceivedDateTerm;
import com.google.code.javax.mail.search.SearchTerm;
import com.google.code.javax.mail.search.SentDateTerm;
import com.google.code.javax.mail.search.SizeTerm;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public abstract class BaseGmailConnector {

	protected static final String RFC3339 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	protected static final String TIMEZONE = "UTC";
	
	private Date parseDate(String date, String format) {
	    DateFormat df = new SimpleDateFormat(format);
	    try {
	    	return df.parse(date);  
	    } catch (ParseException e) {
	    	throw new IllegalArgumentException(String.format("Could not parse date %s with format %s", date, format));
	    }
	}
	
	/**
	 * 
	 * @param folder
	 * @param receivedBefore
	 * @param receivedAfter
	 * @param sentBefore
	 * @param sentAfter
	 * @param fromAddresses
	 * @param toAddresses
	 * @param messageNumber
	 * @param size
	 * @param flags
	 * @param labels
	 * @param rawSearchTerms
	 * @param threadId
	 * @return
	 */
	@Processor
	public List<IMAPMessage> getMessages(
					org.mule.module.gmail.model.Folder folder,
					@Optional String receivedBefore,
					@Optional String receivedAfter,
					@Optional String sentBefore,
					@Optional String sentAfter,
					@Optional List<String> fromAddresses,
					@Optional List<String> toAddresses,
					@Optional Integer messageNumber,
					@Optional SearchCriteria size,
					@Optional List<FlagCriteria> flags,
					@Optional List<String> labels,
					@Optional List<String> rawSearchTerms,
					@Optional String threadId,
					@Optional List<String> bodyTerms,
					@Optional List<String> headerTerms,
					@Optional String messageId,
					@Optional List<String> subjectTerms,
					@Optional @Default(RFC3339) String dateFormat) throws MessagingException {
		
		List<SearchTerm> searchTerms = new ArrayList<SearchTerm>();
		
		if (!StringUtils.isBlank(receivedBefore)) {
			searchTerms.add(new ReceivedDateTerm(ComparisonTerm.LE, this.parseDate(receivedBefore, dateFormat)));
		}
		
		if (!StringUtils.isBlank(receivedAfter)) {
			searchTerms.add(new ReceivedDateTerm(ComparisonTerm.GE, this.parseDate(receivedAfter, dateFormat)));
		}
		
		if (!StringUtils.isBlank(sentBefore)) {
			searchTerms.add(new SentDateTerm(ComparisonTerm.LE, this.parseDate(sentBefore, dateFormat)));
		}
		
		if (!StringUtils.isBlank(sentAfter)) {
			searchTerms.add(new SentDateTerm(ComparisonTerm.GE, this.parseDate(sentAfter, dateFormat)));
		}
		
		if (!CollectionUtils.isEmpty(fromAddresses)) {
			List<SearchTerm> from = new ArrayList<SearchTerm>(fromAddresses.size());
			for (String address : fromAddresses) {
				from.add(new FromTerm(new InternetAddress(address)));
			}
			
			searchTerms.add(new OrTerm(from.toArray(new SearchTerm[from.size()])));
		}
		
		if (!CollectionUtils.isEmpty(toAddresses)) {
			List<SearchTerm> to = new ArrayList<SearchTerm>(toAddresses.size());
			for (String address : toAddresses) {
				to.add(new FromTerm(new InternetAddress(address)));
			}
			
			searchTerms.add(new OrTerm(to.toArray(new SearchTerm[to.size()])));
		}
		
		if (messageNumber != null) {
			searchTerms.add(new MessageNumberTerm(messageNumber));
		}
		
		if (size != null) {
			searchTerms.add(new SizeTerm(size.getOperator().getTerm(), Integer.valueOf(size.getValue())));
		}
		
		if (!CollectionUtils.isEmpty(flags)) {
			List<SearchTerm> f = new ArrayList<SearchTerm>(flags.size());
			for (FlagCriteria criteria : flags) {
				f.add(new FlagTerm(criteria.getFlag().getValue(), criteria.isSet()));
			}
			searchTerms.add(new OrTerm(f.toArray(new SearchTerm[f.size()])));
		}
		
		if (!CollectionUtils.isEmpty(labels)) {
			for (String label : labels) {
				searchTerms.add(new GmailLabelTerm(label));
			}
		}
		
		if (!CollectionUtils.isEmpty(rawSearchTerms)) {
			for (String term : rawSearchTerms) {
				searchTerms.add(new GmailRawSearchTerm(term));
			}
		}
		
		
		
		
//		SearchTerm searchTerm = this.getSearchTerms(addresses, request.getLastEditedDate(), request.getEmailLabel(), inbound);
//		Message[] messages = folder.search(searchTerm);
//		
//		if (messages.length == 0) {
//			return new ArrayList<SyncEmail>();
//		}
		
		FetchProfile fp = new FetchProfile();
        fp.add(IMAPFolder.FetchProfileItem.X_GM_MSGID);
        fp.add(IMAPFolder.FetchProfileItem.X_GM_THRID);
        fp.add(IMAPFolder.FetchProfileItem.X_GM_LABELS);
        
        Store store = this.getStore();
		Folder imapFolder = this.openFolder(store, folder);
        
//        folder.fetch(messages, fp);
        
        return null;
	}
	
	
	protected abstract Store getStore();
	
	private Folder openFolder(Store store, org.mule.module.gmail.model.Folder gmailFolder) throws MessagingException {
		String target = gmailFolder.getDescription(); 
		for (Folder f : store.getDefaultFolder().xlist("*")) {
			IMAPFolder folder = (IMAPFolder) f;
			for (String attr : folder.getAttributes()) {
				attr = attr.substring(1);
				if (target.equals(attr)) { // xlist returns things like \Inbox so remove first character
					f = store.getFolder(target.equals("Inbox") ? target : folder.getFullName());
					f.open(Folder.READ_ONLY);
					return f;
				}
			}
		}
		
		throw new IllegalArgumentException(String.format("No such folder %s", target));
	}
	
}
