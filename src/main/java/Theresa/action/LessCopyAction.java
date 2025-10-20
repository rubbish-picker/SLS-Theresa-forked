package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class LessCopyAction extends AbstractGameAction {
    public LessCopyAction(AbstractCard cardToCopy,int copyTimes) {
        this.c = cardToCopy;
        this.amount = copyTimes;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    AbstractCard c;

    @Override
    public void update() {
        if (duration == startDuration) {
            if(!DustPatch.dustManager.isFull()){
                int empty = DustPatch.dustManager.dustUpLimit-DustPatch.dustManager.dustCards.size();
                if(this.amount > empty)
                    amount = empty;
                for(int i=0;i<amount;i++) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    copy.resetAttributes();
                    DustPatch.dustManager.addCard(copy);
                }
            }
            else {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}
