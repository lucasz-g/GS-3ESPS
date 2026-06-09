import React, { useEffect, useState } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';

type Props = NativeStackScreenProps<RootStackParamList, 'Home'>;

/**
 * Tela de dashboard que exibe um resumo do relatório de risco mais recente.
 * Também oferece atalhos para a lista de locais e para o histórico de
 * relatórios.
 */
export default function HomeScreen({ navigation }: Props) {
  const [latest, setLatest] = useState<any | null>(null);

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
    <View style={styles.container}>
      <Text style={styles.title}>Dashboard</Text>
      {latest ? (
        <View style={styles.card}>
          <Text style={styles.cardTitle}>{latest.locationName}</Text>
          <Text>Temperatura: {latest.temperature}°C</Text>
          <Text>Vento: {latest.windSpeed} km/h</Text>
          <Text>Chuva: {latest.rainProbability}%</Text>
          <Text>Risco: {latest.riskLevel}</Text>
          <Text>Recomendação: {latest.recommendation}</Text>
        </View>
      ) : (
        <Text>Nenhum relatório disponível.</Text>
      )}
      <Button title="Locais" onPress={() => navigation.navigate('Locations')} />
      <Button title="Histórico" onPress={() => navigation.navigate('History')} />
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
