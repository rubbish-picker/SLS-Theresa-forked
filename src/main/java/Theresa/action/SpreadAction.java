package Theresa.action;

import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpreadAction extends AbstractGameAction {
    public SpreadAction(CardGroup.CardGroupType type){
        this.t = type;
    }

    @Override
    public void update() {
        if(t== CardGroup.CardGroupType.DRAW_PILE)
            SilkPatch.expandSingleSilk(AbstractDungeon.player.drawPile.group);
        else if(t== CardGroup.CardGroupType.HAND)
            SilkPatch.expandSingleSilk(AbstractDungeon.player.hand.group);
        else if(t== CardGroup.CardGroupType.DISCARD_PILE)
            SilkPatch.expandSingleSilk(AbstractDungeon.player.discardPile.group);
        else if(t== OtherEnum.Theresa_Dust)
            SilkPatch.expandDustSilk();

        this.isDone = true;
    }

    CardGroup.CardGroupType t;
}
