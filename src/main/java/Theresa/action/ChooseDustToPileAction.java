package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.patch.DustPatch;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ChooseDustToPileAction extends AbstractGameAction {
    public ChooseDustToPileAction(CardGroup.CardGroupType type, int amount) {
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.gType = type;
    }

    public ChooseDustToPileAction setAnyNumber(){
        anyNumber = true;
        return this;
    }

    public ChooseDustToPileAction setCopy(int copyTimes,boolean exhaust,boolean ethereal){
        this.isCopy = true;
        this.copyTimes = copyTimes;
        this.exhaustCopy = exhaust;
        this.etherealCopy = ethereal;
        return this;
    }

    boolean anyNumber = false;
    boolean isCopy = false;
    int copyTimes;
    boolean exhaustCopy;
    boolean etherealCopy;

    CardGroup.CardGroupType gType;
    boolean selected = false;

    @Override
    public void update() {
        if (duration == startDuration) {
            selected = true;
            ArrayList<AbstractCard> dusts = new ArrayList<>();
            for(AbstractCard c : DustPatch.dustManager.dustCards) {
                dusts.add(c.makeSameInstanceOf());
            }
            if (dusts.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (amount > dusts.size()) {
                amount = dusts.size();
            }
            if(gType== CardGroup.CardGroupType.HAND && amount > 10-AbstractDungeon.player.hand.size()) {
                amount = 10-AbstractDungeon.player.hand.size();
            }
            if(amount<=0){
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.group = dusts;
            String des = StringHelper.OPERATION.TEXT[anyNumber?7:0] + amount + StringHelper.OPERATION.TEXT[1];
            if (gType == CardGroup.CardGroupType.DRAW_PILE)
                des += StringHelper.OPERATION.TEXT[2];
            else if (gType == CardGroup.CardGroupType.HAND)
                des += StringHelper.OPERATION.TEXT[3];
            else if (gType == CardGroup.CardGroupType.DISCARD_PILE)
                des += StringHelper.OPERATION.TEXT[4];
            else if (gType == CardGroup.CardGroupType.EXHAUST_PILE)
                des += StringHelper.OPERATION.TEXT[5];
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, anyNumber, des);
        }
        else if (selected) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractCard card = DustPatch.dustManager.getInstanceCard(c);
                if (card != null) {
                    if(!isCopy || gType == CardGroup.CardGroupType.EXHAUST_PILE)
                        DustPatch.dustManager.removeCard(card);
                    CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    tmp.addToTop(card);
                    if (gType == CardGroup.CardGroupType.DRAW_PILE) {
                        if(!isCopy)
                            tmp.moveToDeck(card, true);
                        else {
                            AbstractCard ec = card.makeStatEquivalentCopy();
                            if(etherealCopy)
                                CardModifierManager.addModifier(ec,new EtherealMod());
                            if(exhaustCopy)
                                CardModifierManager.addModifier(ec,new ExhaustMod());
                            addToTop(new MakeTempCardInDrawPileAction(ec,copyTimes,true,true));
                        }
                    } else if (gType == CardGroup.CardGroupType.HAND) {
                        if(!isCopy)
                            tmp.moveToHand(card);
                        else {
                            AbstractCard ec = card.makeStatEquivalentCopy();
                            if(etherealCopy)
                                CardModifierManager.addModifier(ec,new EtherealMod());
                            if(exhaustCopy)
                                CardModifierManager.addModifier(ec,new ExhaustMod());
                            addToTop(new MakeTempCardInHandAction(ec,copyTimes));
                        }
                    } else if (gType == CardGroup.CardGroupType.DISCARD_PILE) {
                        if(!isCopy)
                            tmp.moveToDiscardPile(card);
                        else {
                            AbstractCard ec = card.makeStatEquivalentCopy();
                            if(etherealCopy)
                                CardModifierManager.addModifier(ec,new EtherealMod());
                            if(exhaustCopy)
                                CardModifierManager.addModifier(ec,new ExhaustMod());
                            addToTop(new MakeTempCardInDiscardAction(ec,copyTimes));
                        }
                    } else if (gType == CardGroup.CardGroupType.EXHAUST_PILE) {
                        tmp.moveToExhaustPile(card);
                    }
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }


        this.tickDuration();
    }
}
