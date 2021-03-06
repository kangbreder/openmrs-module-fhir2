/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.impl;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Setter;
import org.hl7.fhir.r4.model.ListResource;
import org.openmrs.Cohort;
import org.openmrs.module.fhir2.api.FhirListService;
import org.openmrs.module.fhir2.api.dao.FhirListDao;
import org.openmrs.module.fhir2.api.translators.ListTranslator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Setter(AccessLevel.PACKAGE)
public class FhirCohortListServiceImpl implements FhirListService<Cohort> {
	
	@Inject
	private FhirListDao<Cohort> dao;
	
	@Inject
	private ListTranslator<Cohort> cohortListTranslator;
	
	@Override
	@Transactional(readOnly = true)
	public ListResource getListByUuid(String uuid) {
		return cohortListTranslator.toFhirResource(dao.getListByUuid(uuid));
	}
}
