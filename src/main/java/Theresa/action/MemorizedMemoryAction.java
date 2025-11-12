package Theresa.action;

import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MemorizedMemoryAction extends AbstractGameAction {
    public MemorizedMemoryAction() {}

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cards = AbstractDungeon.player.hand.group;
        MindSilk silk = new MindSilk();
        boolean canGo = false;
        for(AbstractCard c : cards) {
            AbstractSilk aS = SilkPatch.SilkCardField.silk.get(c);
            if(aS == null || silk.canReplace(aS)) {
                canGo = true;
                break;
            }
        }
        if(canGo) {
            addToTop(new MemorizedMemoryAction());
            addToTop(new RandomSilkAction(silk.makeCopy(),false,true));
            addToTop(new RandomSilkAction(silk.makeCopy(),false,true));
            addToTop(new DrawCardAction(1));
        }

        this.isDone = true;
    }
}
