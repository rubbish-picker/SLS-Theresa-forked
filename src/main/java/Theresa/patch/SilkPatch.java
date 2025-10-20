package Theresa.patch;

import Theresa.action.MemorySilkAction;
import Theresa.action.SetSilkAction;
import Theresa.action.YoreLingerAction;
import Theresa.power.buff.YoreLingerPower;
import Theresa.relic.TheEnd;
import Theresa.silk.AbstractSilk;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class SilkPatch {

    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class SilkCardField {
        public static SpireField<Integer> silkTriggerTimes = new SpireField<>(() -> 1);
        public static SpireField<AbstractSilk> silk = new SpireField<>(() -> null);
        public static SpireField<AbstractSilk> silkForPreview = new SpireField<>(() -> null);
    }

    @SpirePatch(clz = AbstractCard.class,method = "renderEnergy",paramtypez = {SpriteBatch.class})
    public static class RenderCardPatch{
        @SpireInsertPatch(
                rloc = 0,
                localvars = {"renderColor"}
        )
        public static void Insert(AbstractCard _inst, SpriteBatch sb, Color renderColor) {
            AbstractSilk silk = SilkCardField.silk.get(_inst);
            if (silk != null) {
                silk.render(sb, renderColor.cpy());
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class,method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch{
        @SpirePostfixPatch
        public static AbstractCard Postfix(AbstractCard _ret, AbstractCard _inst){
            AbstractSilk silk = SilkCardField.silk.get(_inst);
            if (silk != null && _ret != null) {
                AbstractSilk copy = silk.makeCopy();
                copy.amount = silk.amount;
                //复制时不触发
                setSilkWithoutTrigger(_ret,copy);
                copy.applyPowers();
            }

            return _ret;
        }
    }

    public static void setSilk(AbstractCard card, AbstractSilk silk){
        silk.card = card;
        SilkCardField.silk.set(card, silk);

        //trigger
        if (AbstractDungeon.player!=null) {
            silk.onCopied();

            AbstractRelic r = AbstractDungeon.player.getRelic(TheEnd.ID);
            if(AbstractDungeon.actionManager != null && r instanceof TheEnd){
                ((TheEnd) r).onTriggerSilk(card);
            }
        }
    }

    public static void setSilkWithoutTrigger(AbstractCard card, AbstractSilk silk){
        silk.card = card;
        SilkCardField.silk.set(card, silk);
    }

    public static void setSilkForPreview(AbstractCard card, AbstractSilk silk) {
        SilkCardField.silkForPreview.set(card, silk);
    }

    public static void triggerSilk(TriggerType triggerType,AbstractCard card, CardGroup.CardGroupType type){
        AbstractSilk silk = SilkCardField.silk.get(card);
        if (silk != null) {
            int triggerTimes = SilkCardField.silkTriggerTimes.get(card);
            for(int i =0; i<triggerTimes; i++){
                if(triggerType==TriggerType.ALL || triggerType==TriggerType.TURN_START)
                    silk.atTurnStart(type);
                if(triggerType==TriggerType.ALL || triggerType==TriggerType.TURN_END)
                    silk.atTurnEnd(type);
                if(triggerType==TriggerType.ALL || triggerType==TriggerType.AFTER_PLAYED)
                    silk.afterPlayed();
                if(triggerType==TriggerType.ALL || triggerType==TriggerType.ON_COPIED)
                    silk.onCopied();
            }
        }
    }

    public static void atTurnStart(){
        MemorySilkAction.thirdCards.clear();

        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            triggerSilk(TriggerType.TURN_START,c, CardGroup.CardGroupType.DRAW_PILE);
        }

        for(AbstractCard c : AbstractDungeon.player.hand.group){
            triggerSilk(TriggerType.TURN_START,c, CardGroup.CardGroupType.HAND);
        }

        for(AbstractCard c : AbstractDungeon.player.discardPile.group){
            triggerSilk(TriggerType.TURN_START,c, CardGroup.CardGroupType.DISCARD_PILE);
        }

        for(AbstractCard c : DustPatch.dustManager.dustCards){
            triggerSilk(TriggerType.TURN_START,c,OtherEnum.Theresa_Dust);
        }
    }

    public static void atTurnEnd(){
        //expandAllSilk();
        expandSingleSilk(AbstractDungeon.player.hand.group);
        expandDustSilk();

        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            triggerSilk(TriggerType.TURN_END,c, CardGroup.CardGroupType.DRAW_PILE);
        }

        for(AbstractCard c : AbstractDungeon.player.hand.group){
            triggerSilk(TriggerType.TURN_END,c, CardGroup.CardGroupType.HAND);
        }

        for(AbstractCard c : AbstractDungeon.player.discardPile.group){
            triggerSilk(TriggerType.TURN_END,c, CardGroup.CardGroupType.DISCARD_PILE);
        }

        for(AbstractCard c : DustPatch.dustManager.dustCards){
            triggerSilk(TriggerType.TURN_END,c,OtherEnum.Theresa_Dust);
        }
    }

    public static void expandAllSilk(){
        expandSingleSilk(AbstractDungeon.player.drawPile.group);
        expandSingleSilk(AbstractDungeon.player.hand.group);
        expandSingleSilk(AbstractDungeon.player.discardPile.group);
        expandDustSilk();
    }

    public static void applyPowerToCard(AbstractCard c){
        AbstractSilk silk = SilkCardField.silk.get(c);
        if(silk != null){
            silk.applyPowers();
        }
    }

    public static void playedCard(AbstractCard c){
        AbstractSilk silk = SilkCardField.silk.get(c);
        if(silk != null){
            silk.afterPlayed();
        }
    }

    public static void expandSingleSilk(ArrayList<AbstractCard> group){
        if(group.size() < 2)
            return;
        AbstractCard lastCard = null;
        AbstractCard currentCard = null;
        AbstractCard nextCard = null;
        for(int i =0;i<group.size();i++){
            if(i>=1){
                lastCard = group.get(i-1);
            }
            currentCard = group.get(i);
            if(i<group.size()-1){
                nextCard = group.get(i+1);
            }
            else
                nextCard = null;
            AbstractSilk silk = SilkCardField.silk.get(currentCard);
            if(silk != null){
                if(lastCard!=null && silk.canSpreadAtTurnEnd(lastCard))
                    AbstractDungeon.actionManager.addToBottom(new SetSilkAction(lastCard,silk.makeCopy()));
                if(nextCard!=null && silk.canSpreadAtTurnEnd(nextCard))
                    AbstractDungeon.actionManager.addToBottom(new SetSilkAction(nextCard,silk.makeCopy()));
            }
        }
    }

    //微尘首尾相连
    public static void expandDustSilk(){
        ArrayList<AbstractCard> group = DustPatch.dustManager.dustCards;
        if(group.size() < 2)
            return;
        AbstractCard lastCard = null;
        AbstractCard currentCard = null;
        AbstractCard nextCard = null;
        for(int i =0;i<group.size();i++){
            if(i>=1){
                lastCard = group.get(i-1);
            }
            else {
                lastCard = group.get(group.size()-1);
            }
            currentCard = group.get(i);
            if(i<group.size()-1){
                nextCard = group.get(i+1);
            }
            else
                nextCard = group.get(0);

            AbstractSilk silk = SilkCardField.silk.get(currentCard);
            if(silk != null){
                if(lastCard!=null && lastCard!=currentCard && silk.canSpreadAtTurnEnd(lastCard))
                    AbstractDungeon.actionManager.addToBottom(new SetSilkAction(lastCard,silk.makeCopy()));
                if(nextCard!=null && nextCard!=currentCard && nextCard!=lastCard && silk.canSpreadAtTurnEnd(nextCard))
                    AbstractDungeon.actionManager.addToBottom(new SetSilkAction(nextCard,silk.makeCopy()));
            }
        }

    }

    public enum TriggerType{
        ALL,
        TURN_START,
        TURN_END,
        AFTER_PLAYED,
        ON_COPIED
    }
}
