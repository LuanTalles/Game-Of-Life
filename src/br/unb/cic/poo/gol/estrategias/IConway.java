/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.poo.gol.estrategias;

import com.google.inject.ImplementedBy;
import br.unb.cic.poo.gol.EstrategiaDeDerivacao;

/**
 *
 * @author luan
 */

@ImplementedBy(Conway.class)
public interface IConway {
    
    EstrategiaDeDerivacao getEstrategia();
    
}
