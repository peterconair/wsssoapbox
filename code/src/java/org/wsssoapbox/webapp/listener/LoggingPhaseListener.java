package org.wsssoapbox.webapp.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingPhaseListener implements PhaseListener {

   private static final long serialVersionUID = 5805974145765679633L;
   private static final Logger logger = LoggerFactory.getLogger(LoggingPhaseListener.class);

   @Override
   public void afterPhase(PhaseEvent phaseEvent) {
      if (logger.isDebugEnabled()) {
         logger.debug("AFTER PHASE: " + phaseEvent.getPhaseId().toString());
      }
   }

   @Override
   public void beforePhase(PhaseEvent phaseEvent) {
      if (logger.isDebugEnabled()) {
         logger.debug("BEFORE PHASE: " + phaseEvent.getPhaseId().toString());
         if (phaseEvent.getPhaseId().toString().equals("PROCESS_VALIDATIONS 3")) {
              logger.debug("Validate phase ");
              //logger.debug("Focus on this : " + phaseEvent.getFacesContext().getViewRoot().);
            phaseEvent.getFacesContext().validationFailed();
         }

      }
   }

   @Override
   public PhaseId getPhaseId() {
      return PhaseId.ANY_PHASE;
   }
}
