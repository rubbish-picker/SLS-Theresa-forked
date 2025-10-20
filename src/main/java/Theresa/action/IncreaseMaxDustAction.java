package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncreaseMaxDustAction extends AbstractGameAction {
    public IncreaseMaxDustAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        DustPatch.dustManager.dustUpLimit += this.amount;
        if(DustPatch.dustManager.dustUpLimit > 10) {
            DustPatch.dustManager.dustUpLimit = 10;
            addToTop(new TalkAction(AbstractDungeon.player,"..."));
        }
        else if(DustPatch.dustManager.dustUpLimit < 0) {
            DustPatch.dustManager.dustUpLimit = 0;
            addToTop(new TalkAction(AbstractDungeon.player,"..."));
        }
        this.isDone = true;
    }
}
