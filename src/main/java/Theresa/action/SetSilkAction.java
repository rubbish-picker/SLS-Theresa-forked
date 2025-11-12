package Theresa.action;

import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SetSilkAction extends AbstractGameAction {
    public SetSilkAction(AbstractCard targetCard, AbstractSilk silk, boolean mustReplace,boolean canReplace) {
        this.card = targetCard;
        this.silk = silk;
        this.mustReplace = mustReplace;
        this.canReplace = canReplace;
    }

    public SetSilkAction(AbstractCard targetCard, AbstractSilk silk) {
        this(targetCard, silk, false,true);
    }

    @Override
    public void update() {
        AbstractSilk tmp = SilkPatch.SilkCardField.silk.get(card);
        if (silk.canSetWhenSet(card) && (tmp == null || mustReplace || (canReplace&&silk.canReplace(tmp)))) {
            //暂时不复制数值
//            if(tmp!=null && silk.canReplace(tmp))
//                silk.amount = tmp.amount;
            SilkPatch.setSilk(card, silk);
            silk.applyPowers();
        }

        this.isDone = true;
    }

    boolean mustReplace = false;
    boolean canReplace = false;
    AbstractCard card;
    AbstractSilk silk;
}
