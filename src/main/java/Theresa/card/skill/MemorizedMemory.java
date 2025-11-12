package Theresa.card.skill;

import Theresa.action.MemorizedMemoryAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MemorizedMemory extends AbstractTheresaCard {
    public static final String ID = "theresa:MemorizedMemory";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public MemorizedMemory() {
        super(ID, cardStrings.NAME, 3, cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
        SilkPatch.setSilkForPreview(this,new MindSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new MemorizedMemoryAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}


