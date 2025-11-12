package Theresa.action;

import Theresa.helper.TheresaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

public class MindOceanAction extends AbstractGameAction {
    public MindOceanAction(int amt) {
        this.amount = amt;
    }

    @Override
    public void update() {
        int increase = 0;
        for(AbstractCard c : new ArrayList<>(AbstractDungeon.player.masterDeck.group)) {
            if(TheresaHelper.connectedToHH(c)){
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(c);
                increase += amount;
            }
        }
        if(increase>0){
            AbstractDungeon.player.increaseMaxHp(increase, true);
        }

        this.isDone = true;
    }
}
