package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DustAllSilkAction extends AbstractGameAction {
    public DustAllSilkAction(AbstractSilk silk) {
        this.silk = silk;
    }

    @Override
    public void update() {
        for(AbstractCard c: DustPatch.dustManager.dustCards){
            AbstractSilk s = SilkPatch.SilkCardField.silk.get(c);
            if(s==null){
                AbstractSilk newSilk = silk.makeCopy();
                SilkPatch.setSilk(c,newSilk);
                newSilk.applyPowers();
            }
        }

        this.isDone = true;
    }

    AbstractSilk silk;
}
