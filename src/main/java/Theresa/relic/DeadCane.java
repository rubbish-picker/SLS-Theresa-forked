package Theresa.relic;

import Theresa.helper.StringHelper;
import Theresa.power.debuff.DyingPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

public class DeadCane extends CustomRelic {
    public static final String ID = "theresa:DeadCane";

    public DeadCane() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = 3;
    }

    @Override
    public void atTurnStart() {
        int amt = Math.max(counter,3);
        this.flash();
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()){
                addToBot(new RelicAboveCreatureAction(m,this));
                addToBot(new ApplyPowerAction(m,AbstractDungeon.player,new DyingPower(m,amt),amt));
            }
        }
        counter = 3;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target!= AbstractDungeon.player){
            this.counter++;
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if(m!=null){
            AbstractDungeon.player.heal(1,true);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


