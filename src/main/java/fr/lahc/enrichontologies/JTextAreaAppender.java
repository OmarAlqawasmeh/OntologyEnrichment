package fr.lahc.enrichontologies;
import javax.swing.JTextArea;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 */

/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
public class JTextAreaAppender extends AppenderSkeleton {
	String NEW_LINE = System.getProperty("line.separator");
	private JTextArea jTextArea;

	/**
	 * Constructor.
	 * 
	 * @param jTextArea
	 */
	public JTextAreaAppender(final JTextArea jTextArea) {
		if (jTextArea == null) {
			throw new IllegalArgumentException("JTextArea has to be not-null.");
		}
		this.jTextArea = jTextArea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#close()
	 */
	public void close() {
		jTextArea = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */

//	  protected void append(LoggingEvent event) 
//	    {
//	        if(event.getLevel().equals(Level.INFO)){
//	        jTextA.append(event.getMessage().toString());
//	        }
//	    }
	@Override
	protected void append(LoggingEvent event) {
		if (layout == null) {
			errorHandler.error("No layout for appender " + name, null, ErrorCode.MISSING_LAYOUT);
			return;
		}
		final String message = layout.format(event);
		jTextArea.append(message);

		if (layout.ignoresThrowable()) {
			for (String throwableRow : ArrayUtils.nullToEmpty(event.getThrowableStrRep())) {
				jTextArea.append(throwableRow + NEW_LINE);
			}
		}
		
		jTextArea.update(jTextArea.getGraphics());
//	    DefaultCaret caret = (DefaultCaret)jTextArea.getCaret();
//	    caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

		// scroll TextArea
//		jTextArea.setCaretPosition(jTextArea.getText().length());
//		jTextArea.update(jTextArea.getGraphics());
//		texta.setCaretPosition(texta.getDocument().getLength() - 1);

	}

}