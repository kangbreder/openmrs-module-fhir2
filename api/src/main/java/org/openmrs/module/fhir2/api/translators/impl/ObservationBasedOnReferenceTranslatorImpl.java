/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.translators.impl;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Setter;
import org.hl7.fhir.r4.model.Reference;
import org.openmrs.Order;
import org.openmrs.TestOrder;
import org.openmrs.module.fhir2.FhirConstants;
import org.openmrs.module.fhir2.api.dao.FhirMedicationRequestDao;
import org.openmrs.module.fhir2.api.dao.FhirServiceRequestDao;
import org.openmrs.module.fhir2.api.translators.ObservationBasedOnReferenceTranslator;
import org.springframework.stereotype.Component;

@Component
@Setter(AccessLevel.PACKAGE)
public class ObservationBasedOnReferenceTranslatorImpl extends AbstractReferenceHandlingTranslator implements ObservationBasedOnReferenceTranslator {
	
	@Inject
	private FhirServiceRequestDao<TestOrder> serviceRequestDao;
	
	@Inject
	private FhirMedicationRequestDao medicationRequestDao;
	
	@Override
	public Reference toFhirResource(Order order) {
		if (order == null) {
			return null;
		}
		
		return createOrderReference(order);
	}
	
	@Override
	public Order toOpenmrsType(Reference reference) {
		if (reference == null) {
			return null;
		}
		
		if (!reference.getType().equals(FhirConstants.SERVICE_REQUEST)
		        && !reference.getType().equals(FhirConstants.MEDICATION)) {
			throw new IllegalArgumentException("Reference must be to a ServiceRequest or MedicationRequest");
		}
		
		String uuid = getReferenceId(reference);
		if (uuid == null) {
			return null;
		}
		
		if (reference.getType().equals(FhirConstants.SERVICE_REQUEST)) {
			return serviceRequestDao.getServiceRequestByUuid(uuid);
		}
		
		return medicationRequestDao.getMedicationRequestByUuid(uuid);
	}
}
