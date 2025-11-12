package Theresa.action;

import Theresa.helper.CivilightHelper;
import Theresa.helper.StringHelper;
import Theresa.modifier.EternalMod;
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

public class CivilightAction extends AbstractGameAction {
    public CivilightAction(CardGroup.CardGroupType type, int amount) {
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.gType = type;
    }

    public CivilightAction setAnyNumber(){
        anyNumber = true;
        return this;
    }

    public CivilightAction setCostDiff(int diff){
        this.costDiff = diff;
        return this;
    }

    public CivilightAction upgradeSource(){
        this.upgradeSource = true;
        return this;
    }

    public CivilightAction setCopy(int copyTimes,boolean exhaust,boolean ethereal){
        this.isCopy = true;
        this.copyTimes = copyTimes;
        this.exhaustCopy = exhaust;
        this.etherealCopy = ethereal;
        return this;
    }

    public CivilightAction setType(boolean canAttack, boolean canSkill, boolean canPower, boolean canStatus, boolean canCurse){
        this.canAttack = canAttack;
        this.canSkill = canSkill;
        this.canPower = canPower;
        this.canStatus = canStatus;
        this.canCurse = canCurse;
        return this;
    }

    boolean canAttack = true;
    boolean canSkill = true;
    boolean canPower = true;
    boolean canStatus = true;
    boolean canCurse = true;

    boolean anyNumber = false;
    boolean isCopy = false;
    int copyTimes;
    boolean exhaustCopy;
    boolean etherealCopy;
    int costDiff = 0;
    boolean upgradeSource = false;

    CardGroup.CardGroupType gType;
    boolean selected = false;

    @Override
    public void update() {
        if (duration == startDuration) {
            selected = true;
            ArrayList<AbstractCard> records = new ArrayList<>();
            for(AbstractCard c : CivilightHelper.cardSaveList) {
                if(CivilightHelper.contains(c))
                    continue;
                boolean added = false;
                if(c.type== AbstractCard.CardType.ATTACK&&canAttack)
                    added = true;
                else if(c.type==AbstractCard.CardType.SKILL&&canSkill)
                    added = true;
                else if(c.type==AbstractCard.CardType.POWER&&canPower)
                    added = true;
                else if(c.type==AbstractCard.CardType.STATUS&&canStatus)
                    added = true;
                else if(c.type==AbstractCard.CardType.CURSE&&canCurse)
                    added = true;
                if(added)
                    records.add(c.makeSameInstanceOf());
            }
            if (records.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (amount > records.size()) {
                amount = records.size();
            }
            if(gType== CardGroup.CardGroupType.HAND && amount > 10- AbstractDungeon.player.hand.size()) {
                amount = 10-AbstractDungeon.player.hand.size();
            }
            if(amount<=0){
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.group = records;
            String des = StringHelper.OPERATION.TEXT[anyNumber?7:0] + amount + StringHelper.OPERATION.TEXT[10];
            if (gType == CardGroup.CardGroupType.DRAW_PILE)
                des += StringHelper.OPERATION.TEXT[2];
            else if (gType == CardGroup.CardGroupType.HAND)
                des += StringHelper.OPERATION.TEXT[3];
            else if (gType == CardGroup.CardGroupType.DISCARD_PILE)
                des += StringHelper.OPERATION.TEXT[4];
            else if (gType == CardGroup.CardGroupType.EXHAUST_PILE)
                des += StringHelper.OPERATION.TEXT[5];
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, des,false,false,false,false);
        }
        else if (selected) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if(!CivilightHelper.contains(c))
                    CivilightHelper.civilightThisCombat.add(c);
                if(c.cost>0 && costDiff!=0){
                    int tmp = c.costForTurn+costDiff;
                    if(tmp<0)
                        tmp = 0;
                    c.setCostForTurn(tmp);
                }
                if(upgradeSource){
                    AbstractCard source = CivilightHelper.getInstanceFromSave(c);
                    if(source!=null && source.canUpgrade()){
                        source.upgrade();
                    }
                    if(c.canUpgrade())
                        c.upgrade();
                }
                //去除存续
                boolean tmpExhaust = exhaustCopy;
                if(CardModifierManager.hasModifier(c,EternalMod.ID)){
                    CardModifierManager.removeModifiersById(c, EternalMod.ID,true);
                    tmpExhaust = false;
                }
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.addToTop(c);
                if (gType == CardGroup.CardGroupType.DRAW_PILE) {
                    if(!isCopy)
                        tmp.moveToDeck(c, true);
                    else {
                        AbstractCard ec = c.makeStatEquivalentCopy();
                        if(etherealCopy)
                            CardModifierManager.addModifier(ec,new EtherealMod());
                        if(tmpExhaust)
                            CardModifierManager.addModifier(ec,new ExhaustMod());
                        addToTop(new MakeTempCardInDrawPileAction(ec,copyTimes,true,true));
                    }
                } else if (gType == CardGroup.CardGroupType.HAND) {
                    if(!isCopy)
                        tmp.moveToHand(c);
                    else {
                        AbstractCard ec = c.makeStatEquivalentCopy();
                        if(etherealCopy)
                            CardModifierManager.addModifier(ec,new EtherealMod());
                        if(tmpExhaust)
                            CardModifierManager.addModifier(ec,new ExhaustMod());
                        addToTop(new MakeTempCardInHandAction(ec,copyTimes));
                    }
                } else if (gType == CardGroup.CardGroupType.DISCARD_PILE) {
                    if(!isCopy)
                        tmp.moveToDiscardPile(c);
                    else {
                        AbstractCard ec = c.makeStatEquivalentCopy();
                        if(etherealCopy)
                            CardModifierManager.addModifier(ec,new EtherealMod());
                        if(tmpExhaust)
                            CardModifierManager.addModifier(ec,new ExhaustMod());
                        addToTop(new MakeTempCardInDiscardAction(ec,copyTimes));
                    }
                } else if (gType == CardGroup.CardGroupType.EXHAUST_PILE) {
                    tmp.moveToExhaustPile(c);
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }


        this.tickDuration();
    }
}

