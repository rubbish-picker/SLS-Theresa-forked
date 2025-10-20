package Theresa.action;

import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class RandomSilkAction extends AbstractGameAction {
    public RandomSilkAction(AbstractSilk silk, boolean mustReplace, boolean canReplace) {
        this.silk = silk;
        this.mustReplace = mustReplace;
        this.canReplace = canReplace;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cardsTo = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            AbstractSilk silkToReplace = SilkPatch.SilkCardField.silk.get(c);
            if(mustReplace || silkToReplace==null || (canReplace&&silk.canReplace(silkToReplace))) {
                cardsTo.add(c);
            }
        }

        if(!cardsTo.isEmpty()){
            Collections.shuffle(cardsTo,AbstractDungeon.cardRandomRng.random);
            AbstractCard c = cardsTo.get(0);
            addToTop(new SetSilkAction(c,this.silk,this.mustReplace,this.canReplace));
        }

        this.isDone = true;
    }

    AbstractSilk silk;
    boolean mustReplace;
    boolean canReplace;
}
