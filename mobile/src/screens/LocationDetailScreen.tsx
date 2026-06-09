import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';
import { saveReport, getReport } from '../storage/reportStorage';

type Props = NativeStackScreenProps<RootStackParamList, 'LocationDetail'>;

/**
 * Exibe o relatório de risco mais recente de um local específico. O id
 * do local vem pelos parâmetros da rota. Ao montar, a tela solicita os
 * dados mais recentes ao backend.
 */
export default function LocationDetailScreen({ route }: Props) {
  const { id } = route.params;
  const [report, setReport] = useState<any | null>(null);

  useEffect(() => {
    const fetchReport = async () => {
      try {
        const res = await api.get(`/risk/${id}`);
        setReport(res.data);
        // Persiste o relatório para uso offline.
        await saveReport(id, res.data);
      } catch (error) {
        console.error('Erro ao buscar relatório remoto, tentando offline...', error);
        const cached = await getReport(id);
        if (cached) {
          setReport(cached);
        }
      }
    };
    fetchReport();
  }, [id]);

  if (!report) {
    return (
      <View style={styles.container}>
        <Text>Carregando...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{report.locationName}</Text>
      <Text>Temperatura: {report.temperature}°C</Text>
      <Text>Vento: {report.windSpeed} km/h</Text>
      <Text>Chuva: {report.rainProbability}%</Text>
      <Text>Condição: {report.weatherCondition}</Text>
      <Text>Nível de risco: {report.riskLevel}</Text>
      <Text>Recomendação: {report.recommendation}</Text>
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
    marginBottom: 10,
    fontWeight: 'bold'
  }
});
