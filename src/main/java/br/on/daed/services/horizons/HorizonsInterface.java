/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.on.daed.services.horizons;

import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class HorizonsInterface {
	public static void main (String args[]) {
		HttpConnector tc = new HttpConnector();
		
		HorizonsID[] values = HorizonsID.values();
		
		for(int i = 0; i < values.length; i++) {
			String output = (String) tc.query(values[i], HorizonsOptions.CARTESIAN, 2451544.5);
			System.out.println(values[i].name());
			System.out.println(output);
		}
		
	}
}