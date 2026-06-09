import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';
import { useTheme } from '../theme/ThemeContext';

type Props = NativeStackScreenProps<RootStackParamList, 'Login'>;

/**
 * Tela que permite autenticar o usuário. As credenciais são enviadas
 * ao backend e o token retornado é salvo no AsyncStorage para as
 * próximas requisições. Em caso de sucesso, o usuário navega para
 * o dashboard.
 */
export default function LoginScreen({ navigation }: Props) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { colors, isDark, toggleTheme } = useTheme();

  const handleLogin = async () => {
    try {
      const { data } = await api.post('/auth/login', { email, password });
      await AsyncStorage.setItem('token', data.token);
      navigation.replace('Home');
    } catch (error) {
      console.error(error);
      // Em um app real, exiba uma mensagem amigável para o usuário.
    }
  };

  return (
    <View style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.title, { color: colors.text }]}>Login</Text>
      <TextInput
        placeholder="Email"
        placeholderTextColor={colors.placeholder}
        value={email}
        onChangeText={setEmail}
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
        autoCapitalize="none"
        keyboardType="email-address"
      />
      <TextInput
        placeholder="Senha"
        placeholderTextColor={colors.placeholder}
        value={password}
        secureTextEntry
        onChangeText={setPassword}
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
      />
      <Button title="Entrar" onPress={handleLogin} color={colors.primary} />
      <Button
        title="Criar conta"
        onPress={() => navigation.navigate('Register')}
        color={colors.primary}
      />
      <Button
        title={isDark ? 'Usar tema claro' : 'Usar tema escuro'}
        onPress={toggleTheme}
        color={colors.primary}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    paddingHorizontal: 20,
    backgroundColor: '#fff'
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
    textAlign: 'center'
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 10,
    marginBottom: 10,
    borderRadius: 4
  }
});
