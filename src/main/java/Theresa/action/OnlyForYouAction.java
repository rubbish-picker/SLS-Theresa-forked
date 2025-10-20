package Theresa.action;

import Theresa.modifier.OnlyForYouMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class OnlyForYouAction extends AbstractGameAction {
    public OnlyForYouAction() {}

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = AbstractDungeon.player.drawPile.group;
        for (AbstractCard c : cards) {
            CardModifierManager.addModifier(c,new OnlyForYouMod());
        }
        cards = AbstractDungeon.player.hand.group;
        for (AbstractCard c : cards) {
            c.flash();
            CardModifierManager.addModifier(c,new OnlyForYouMod());
        }
        cards = AbstractDungeon.player.discardPile.group;
        for (AbstractCard c : cards) {
            CardModifierManager.addModifier(c,new OnlyForYouMod());
        }
        this.isDone = true;
    }
}
