package org.wsssoapbox.bean.lifecycle;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 5805974145765679633L;
	private static final Log log = LogFactory
			.getLog(LoggingPhaseListener.class);

	public void afterPhase(PhaseEvent phaseEvent) {
		if (log.isInfoEnabled()) {
			log.info("AFTER PHASE: " + phaseEvent.getPhaseId().toString());			
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		if (log.isInfoEnabled()) {
			log.info("BEFORE PHASE: " + phaseEvent.getPhaseId().toString());
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
