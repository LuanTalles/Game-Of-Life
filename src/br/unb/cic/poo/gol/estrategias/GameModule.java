package br.unb.cic.poo.gol.estrategias;

import br.unb.cic.poo.gol.EstrategiaDeDerivacao;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class GameModule implements Module{
    
    @Override
    public void configure(Binder binder) {

        binder.bind(EstrategiaDeDerivacao.class).annotatedWith(Names.named("Conway")).to(Conway.class);
        binder.bind(EstrategiaDeDerivacao.class).annotatedWith(Names.named("LiveFreeOrDie")).to(LiveFreeOrDie.class);
        binder.bind(EstrategiaDeDerivacao.class).annotatedWith(Names.named("HighLife")).to(HighLife.class);
    }
}
