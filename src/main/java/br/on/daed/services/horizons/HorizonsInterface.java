/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.on.daed.services.horizons;

import br.on.daed.services.horizons.objects.HorizonsResult;
import br.on.daed.services.horizons.objects.HorizonsResultCollection;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class HorizonsInterface {
	
	private static final double MIN_JD = 2444240.0;
	private static final double MAX_JD = 2480765.0;
	
	@Autowired
	private HttpConnector tc;
	
	public static boolean validateInputDate(Double date) {
		return date != null && date >= MIN_JD && date <= MAX_JD;
	}
	
	public HorizonsResultCollection fecthResultCollection(Double JD, HorizonsOptions op, HorizonsID[] ids) {
		HorizonsResultCollection ret = null;
		
		if(validateInputDate(JD) && op != null && ids != null && ids.length > 0) {

			ret = new HorizonsResultCollection();

			ret.setJd(JD);
			ret.setOp(op);

			for(int i = 0; i < ids.length; i++) {
				HorizonsResult query = tc.query(ids[i], op, JD);
				if(query == null) {
					throw new UnsupportedOperationException("Error while getting data from Horizons to assemble ResultCollection");
				}
				ret.add(query);
			}
		
		}
		
		return ret;
	}
	
	public HorizonsResultCollection getStandardDynamicalModel (Double JD) {

		// REF J2000 = 2451544.5;
		
		HorizonsOptions op = HorizonsOptions.ORBITAL_ELEMENTS;
		
		HorizonsID[] values = {
			HorizonsID.MERCURY,
			HorizonsID.VENUS,
			HorizonsID.EARTH,
			HorizonsID.MARS,
			HorizonsID.JUPITER,
			HorizonsID.SATURN,
			HorizonsID.NEPTUNE,
			HorizonsID.URANUS,
			HorizonsID.CERES,
			HorizonsID.VESTA,
			HorizonsID.PALLAS
		};

		HorizonsResultCollection ret = this.fecthResultCollection(JD, op, values);

		return ret;
	}
	
}