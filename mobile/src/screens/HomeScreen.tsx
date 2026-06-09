import React, { useEffect, useState } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';
import { useTheme } from '../theme/ThemeContext';

type Props = NativeStackScreenProps<RootStackParamList, 'Home'>;

/**
 * Tela de dashboard que exibe um resumo do relatório de risco mais recente.
 * Também oferece atalhos para a lista de locais e para o histórico de
 * relatórios.
 */
export default function HomeScreen({ navigation }: Props) {
  const [latest, setLatest] = useState<any | null>(null);
  const { colors } = useTheme();

  useEffect(() => {
    const fetchLatest = async () => {
      try {
        const res = await api.get('/risk/history');
        if (Array.isArray(res.data) && res.data.length > 0) {
          setLatest(res.data[0]);
        }
      } catch (error) {
        console.error(error);
      }
    };
    fetchLatest();
  }, []);

  return (
    <View style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.title, { color: colors.text }]}>Dashboard</Text>
      {latest ? (
        <View style={[styles.card, { backgroundColor: colors.card, borderColor: colors.border }]}>
          <Text style={[styles.cardTitle, { color: colors.text }]}>{latest.locationName}</Text>
          <Text style={{ color: colors.text }}>Temperatura: {latest.temperature}°C</Text>
          <Text style={{ color: colors.text }}>Vento: {latest.windSpeed} km/h</Text>
          <Text style={{ color: colors.text }}>Chuva: {latest.rainProbability}%</Text>
          <Text style={{ color: colors.text }}>Risco: {latest.riskLevel}</Text>
          <Text style={{ color: colors.text }}>Recomendação: {latest.recommendation}</Text>
        </View>
      ) : (
        <Text style={{ color: colors.text }}>Nenhum relatório disponível.</Text>
      )}
      <Button title="Locais" onPress={() => navigation.navigate('Locations')} color={colors.primary} />
      <Button title="Histórico" onPress={() => navigation.navigate('History')} color={colors.primary} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff'
  },
  title: {
    fontSize: 24,
    marginBottom: 20
  },
  card: {
    padding: 15,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 4,
    marginBottom: 20
  },
  cardTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10
  }
});
