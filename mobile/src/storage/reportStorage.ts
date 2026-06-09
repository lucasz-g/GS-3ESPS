import AsyncStorage from '@react-native-async-storage/async-storage';

const PREFIX = 'report_';

/**
 * Persiste um relatório de risco no armazenamento local usando o id
 * do local como chave. Apenas o relatório mais recente de cada local
 * é salvo, permitindo fallback offline quando a rede estiver indisponível.
 *
 * @param locationId identificador do local
 * @param report relatório que será persistido
 */
export async function saveReport(locationId: number, report: any): Promise<void> {
  try {
    await AsyncStorage.setItem(`${PREFIX}${locationId}`, JSON.stringify(report));
  } catch (e) {
    console.error('Erro ao salvar relatório', e);
  }
}

/**
 * Recupera do armazenamento local um relatório em cache para o id do
 * local informado. Retorna null quando não há cache ou quando a leitura falha.
 *
 * @param locationId identificador do local
 */
export async function getReport(locationId: number): Promise<any | null> {
  try {
    const json = await AsyncStorage.getItem(`${PREFIX}${locationId}`);
    return json ? JSON.parse(json) : null;
  } catch (e) {
    console.error('Erro ao ler relatório armazenado', e);
    return null;
  }
}
