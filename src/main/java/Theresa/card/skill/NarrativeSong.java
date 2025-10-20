package Theresa.card.skill;

import Theresa.action.SpreadAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.MemorySilk;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NarrativeSong extends AbstractTheresaCard {
    public static final String ID = "theresa:NarrativeSong";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public NarrativeSong() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        SilkPatch.setSilkWithoutTrigger(this,new MemorySilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new EmptyDeckShuffleAction());
        addToBot(new SpreadAction(CardGroup.CardGroupType.DRAW_PILE));
    }


    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}







