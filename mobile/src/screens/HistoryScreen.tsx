import React, { useEffect, useState } from 'react';
import { View, Text, FlatList, StyleSheet } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';
import { useTheme } from '../theme/ThemeContext';

type Props = NativeStackScreenProps<RootStackParamList, 'History'>;

/**
 * Tela que lista os relatórios de risco anteriores do usuário atual em
 * ordem decrescente de criação. Permite consultar rapidamente os dados
 * históricos.
 */
export default function HistoryScreen({}: Props) {
  const [history, setHistory] = useState<any[]>([]);
  const { colors } = useTheme();

  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const res = await api.get('/risk/history');
        setHistory(res.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchHistory();
  }, []);

  return (
    <View style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.title, { color: colors.text }]}>Histórico de Relatórios</Text>
      <FlatList
        data={history}
        keyExtractor={(item) => item.id.toString()}
        renderItem={({ item }) => (
          <View style={[styles.card, { backgroundColor: colors.card, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.text }]}>{item.locationName}</Text>
            <Text style={{ color: colors.text }}>Risco: {item.riskLevel}</Text>
            <Text style={{ color: colors.text }}>Data: {new Date(item.createdAt).toLocaleString()}</Text>
          </View>
        )}
        ListEmptyComponent={<Text style={{ color: colors.text }}>Nenhum relatório encontrado.</Text>}
      />
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
    marginBottom: 10
  },
  cardTitle: {
    fontSize: 18,
    fontWeight: 'bold'
  }
});
