package Theresa.card.attack;

import Theresa.action.EmotionSameAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EmotionSame extends AbstractTheresaCard {
    public static final String ID = "theresa:EmotionSame";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public EmotionSame() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        baseDamage = damage = 6;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int[] randomMultiDamage = new int[multiDamage.length];
        for(int i=0; i<multiDamage.length; i++) {
             randomMultiDamage[i] = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, multiDamage[i]*2);
        }
        addToBot(new DamageAllEnemiesAction(abstractPlayer,randomMultiDamage,damageTypeForTurn,attackEffect));
        addToBot(new EmotionSameAction(com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, 2)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}



