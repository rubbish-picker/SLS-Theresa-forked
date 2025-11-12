package Theresa.relic;

import Theresa.action.IncreaseMaxDustAction;
import Theresa.helper.StringHelper;
import Theresa.patch.DustPatch;
import Theresa.power.buff.EchoismPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TenRings extends CustomRelic {
    public static final String ID = "theresa:TenRings";

    public TenRings() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EchoismPower(AbstractDungeon.player,1),1));
    }

    @Override
    public void onPlayerEndTurn() {
        if(DustPatch.dustManager.dustUpLimit>2){
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new IncreaseMaxDustAction(-2));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EchoismPower(AbstractDungeon.player,1),1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}



